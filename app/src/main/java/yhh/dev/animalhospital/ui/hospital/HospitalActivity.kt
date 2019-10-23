package yhh.dev.animalhospital.ui.hospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import yhh.dev.animalhospital.R
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ENTITY = "entity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HospitalFragment.newInstance()
                    .also {
                        it.arguments = Bundle()
                            .apply {
                                putString(
                                    EXTRA_ENTITY,
                                    intent?.extras?.getString(EXTRA_ENTITY)
                                )
                            }
                    })
                .commitNow()
        }

        title = Gson().fromJson(intent?.extras?.getString(EXTRA_ENTITY), AnimalHospitalEntity::class.java).name
    }
}