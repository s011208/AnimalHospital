package yhh.dev.animalhospital.ui.hospital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import timber.log.Timber
import yhh.dev.animalhospital.lib.firebase.FirebaseDatabaseHelper
import yhh.dev.animalhospital.lib.firebase.entity.CommentEntity
import yhh.dev.repository.local.AnimalHospitalEntity
import java.util.*
import kotlin.collections.ArrayList

class HospitalViewModel(
    private val firebaseHelper: FirebaseDatabaseHelper,
    private val entity: AnimalHospitalEntity
) : ViewModel() {

    val entityData = MutableLiveData<Pair<AnimalHospitalEntity, List<CommentEntity>>>()

    fun fetch() {
        firebaseHelper.commentsReference
            .child(entity.id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    Timber.v("childrenCount: ${snapshot.childrenCount}")
                    val array = ArrayList<CommentEntity>()
                    snapshot.children.forEach { item ->
                        array.add(
                            item.getValue(CommentEntity::class.java)!!
                        )
                    }

                    entityData.value = Pair(entity, array)
                }
            })

        entityData.value = Pair(entity, ArrayList())
    }

    fun addComment(comment: CommentEntity) {
        firebaseHelper.commentsReference
            .child(entity.id)
            .child(comment.id)
            .setValue(comment)
            .addOnCompleteListener {
                fetch()
            }
    }
}