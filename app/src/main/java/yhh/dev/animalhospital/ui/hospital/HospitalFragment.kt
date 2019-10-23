package yhh.dev.animalhospital.ui.hospital

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
import yhh.dev.animalhospital.R
import yhh.dev.animalhospital.ui.hospital.epoxy.HospitalViewController
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalFragment : Fragment() {

    companion object {
        fun newInstance() = HospitalFragment()
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



        comment.setOnClickListener { }
    }

    private fun observeViewModel() {
        hospitalViewModel.entityData.observe(
            this,
            Observer<Pair<AnimalHospitalEntity, List<String>>> { pairData ->
                controller.setData(
                    pairData.first,
                    pairData.second
                )
            })
    }
}