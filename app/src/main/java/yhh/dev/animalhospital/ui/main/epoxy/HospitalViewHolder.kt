package yhh.dev.animalhospital.ui.main.epoxy

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.reactivex.subjects.PublishSubject
import yhh.dev.animalhospital.R
import yhh.dev.animalhospital.lib.epoxy.KotlinHolder
import yhh.dev.repository.local.AnimalHospitalEntity

@EpoxyModelClass(layout = R.layout.epoxy_view_hospital)
abstract class HospitalViewHolder : EpoxyModelWithHolder<HospitalViewHolder.Holder>() {

    @EpoxyAttribute
    lateinit var entity: AnimalHospitalEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var phoneIntent: PublishSubject<AnimalHospitalEntity>

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var mapIntent: PublishSubject<AnimalHospitalEntity>

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemIntent: PublishSubject<AnimalHospitalEntity>

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.name.text = entity.name
        holder.address.text = entity.address

        if (entity.tel.isBlank()) {
            holder.telContainer.visibility = View.GONE
        } else {
            holder.tel.text = entity.tel
            holder.telContainer.visibility = View.VISIBLE
        }
        holder.addressContainer.setOnClickListener { mapIntent.onNext(entity) }
        holder.telContainer.setOnClickListener { phoneIntent.onNext(entity) }
        holder.name.setOnClickListener { itemIntent.onNext(entity) }
    }

    class Holder : KotlinHolder() {
        val name by bind<TextView>(R.id.name)
        val address by bind<TextView>(R.id.address)
        val tel by bind<TextView>(R.id.tel)

        val addressContainer by bind<FrameLayout>(R.id.addressContainer)
        val telContainer by bind<FrameLayout>(R.id.telContainer)
    }
}