package yhh.dev.animalhospital.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import yhh.dev.animalhospital.R
import yhh.dev.animalhospital.ui.hospital.HospitalActivity
import yhh.dev.animalhospital.ui.main.epoxy.HospitalViewController

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

        private const val REQUEST_PHONE_CALL_PERMISSION = 1000
    }

    val mainViewModel: MainViewModel by viewModel()

    private lateinit var controller: HospitalViewController

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        observeViewModel()
    }

    private fun bindViews() {
        errorLabel.setOnClickListener { mainViewModel.fetch() }

        controller = HospitalViewController()
        recyclerView.adapter = controller.adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        compositeDisposable.add(
            controller.clickMapIntent.subscribe {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.co.in/maps?q=${it.address}")
                    )
                )
            }
        )

        compositeDisposable.add(
            controller.clickPhoneIntent.subscribe {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:${it.tel.replace("-", "")}")
                        )
                    )
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_PHONE_CALL_PERMISSION
                    )
                }
            }
        )

        compositeDisposable.add(
            controller.clickItemIntent.subscribe {
                startActivity(
                    Intent(activity!!, HospitalActivity::class.java)
                        .apply {
                            putExtra(
                                HospitalActivity.EXTRA_ENTITY,
                                Gson().toJson(it)
                            )
                        }
                )
            }
        )
    }

    private fun observeViewModel() {
        mainViewModel.getLoading().observe(this,
            Observer<Boolean> { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            })

        mainViewModel.getErrorLabel().observe(this,
            Observer<Boolean> { isError ->
                errorLabel.visibility = if (isError) View.VISIBLE else View.INVISIBLE
            })

        mainViewModel.getAnimalHospitalList().observe(this,
            Observer { list ->
                controller.setData(list)
                recyclerView.visibility = View.VISIBLE
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Timber.v("requestCode: $requestCode")
    }
}
