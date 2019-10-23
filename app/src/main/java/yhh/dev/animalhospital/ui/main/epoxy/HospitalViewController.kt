package yhh.dev.animalhospital.ui.main.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import io.reactivex.subjects.PublishSubject
import yhh.dev.repository.local.AnimalHospitalEntity

class HospitalViewController : TypedEpoxyController<List<AnimalHospitalEntity>>() {

    val clickPhoneIntent: PublishSubject<AnimalHospitalEntity> = PublishSubject.create<AnimalHospitalEntity>()

    val clickMapIntent: PublishSubject<AnimalHospitalEntity> = PublishSubject.create<AnimalHospitalEntity>()

    val clickItemIntent: PublishSubject<AnimalHospitalEntity> = PublishSubject.create<AnimalHospitalEntity>()

    override fun buildModels(data: List<AnimalHospitalEntity>) {
        for (entity in data) {
            hospitalViewHolder {
                id(entity.id)
                entity(entity)
                phoneIntent(clickPhoneIntent)
                mapIntent(clickMapIntent)
                itemIntent(clickItemIntent)
            }
        }
    }
}