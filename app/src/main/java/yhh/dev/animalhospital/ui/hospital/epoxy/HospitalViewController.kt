package yhh.dev.animalhospital.ui.hospital.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import io.reactivex.subjects.PublishSubject
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalViewController: Typed2EpoxyController<AnimalHospitalEntity, List<String>>() {
    val clickPhoneIntent: PublishSubject<AnimalHospitalEntity> = PublishSubject.create<AnimalHospitalEntity>()

    val clickMapIntent: PublishSubject<AnimalHospitalEntity> = PublishSubject.create<AnimalHospitalEntity>()

    override fun buildModels(entity: AnimalHospitalEntity, comments: List<String>) {
        hospitalDetailViewHolder {
            id(entity.id)
            entity(entity)
            phoneIntent(clickPhoneIntent)
            mapIntent(clickMapIntent)
        }
    }
}