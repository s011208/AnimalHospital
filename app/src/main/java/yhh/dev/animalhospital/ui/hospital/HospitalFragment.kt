package yhh.dev.animalhospital.ui.hospital

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_hospital.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import yhh.dev.animalhospital.R
import yhh.dev.animalhospital.lib.firebase.entity.CommentEntity
import yhh.dev.animalhospital.ui.hospital.comment.CommentDialog
import yhh.dev.animalhospital.ui.hospital.epoxy.HospitalViewController
import yhh.dev.repository.local.AnimalHospitalEntity
import java.util.*

class HospitalFragment : Fragment() {

    companion object {
        fun newInstance() = HospitalFragment()

        private const val REQUEST_COMMENT_CODE = 1001
    }

    private lateinit var hospitalViewModel: HospitalViewModel
    private val controller = HospitalViewController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_hospital, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        observeViewModel()
        hospitalViewModel.fetch()
    }

    private fun bindViews() {
        hospitalViewModel = getViewModel {
            parametersOf(
                Gson().fromJson(
                    arguments?.getString(HospitalActivity.EXTRA_ENTITY) ?: "",
                    AnimalHospitalEntity::class.java
                )
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(activity);
        recyclerView.adapter = controller.adapter


        comment.setOnClickListener {
            CommentDialog().also {
                it.setTargetFragment(
                    this@HospitalFragment,
                    REQUEST_COMMENT_CODE
                )
            }.show(fragmentManager, "CommentDialog")
        }
    }

    private fun observeViewModel() {
        hospitalViewModel.entityData.observe(
            this,
            Observer<Pair<AnimalHospitalEntity, List<CommentEntity>>> { pairData ->
                controller.setData(
                    pairData.first,
                    pairData.second
                )
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_COMMENT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                hospitalViewModel.addComment(
                    CommentEntity(
                        data?.getStringExtra(CommentDialog.EXTRA_COMMENT) ?: "",
                        data?.getDoubleExtra(CommentDialog.EXTRA_RATE, 0.0) ?: 0.0,
                        UUID.randomUUID().toString()
                    )
                )
            }
        }
    }
}