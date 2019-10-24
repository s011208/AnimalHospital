package yhh.dev.animalhospital.ui.hospital.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import io.reactivex.subjects.PublishSubject
import yhh.dev.animalhospital.lib.firebase.entity.CommentEntity
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalViewController : Typed2EpoxyController<AnimalHospitalEntity, List<CommentEntity>>() {
    val clickPhoneIntent: PublishSubject<AnimalHospitalEntity> =
        PublishSubject.create<AnimalHospitalEntity>()

    val clickMapIntent: PublishSubject<AnimalHospitalEntity> =
        PublishSubject.create<AnimalHospitalEntity>()

    override fun buildModels(entity: AnimalHospitalEntity, comments: List<CommentEntity>) {
        hospitalDetailViewHolder {
            id(entity.id)
            entity(entity)
            phoneIntent(clickPhoneIntent)
            mapIntent(clickMapIntent)
        }

        comments.forEach { item ->
            hospitalCommentViewHolder {
                id(item.id)
                comment(item.comment)
            }
        }

    }
}