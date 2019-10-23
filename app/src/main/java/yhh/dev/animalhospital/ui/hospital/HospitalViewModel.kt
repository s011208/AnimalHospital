package yhh.dev.animalhospital.ui.hospital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import yhh.dev.animalhospital.lib.firebase.FirebaseDatabaseHelper
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalViewModel(
    private val firebaseHelper: FirebaseDatabaseHelper,
    private val entity: AnimalHospitalEntity
) : ViewModel() {

    val entityData = MutableLiveData<Pair<AnimalHospitalEntity, List<String>>>()

    fun fetch() {
        firebaseHelper.commentsReference
            .child(entity.id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                }
            })

        entityData.value = Pair(entity, ArrayList())
    }
}