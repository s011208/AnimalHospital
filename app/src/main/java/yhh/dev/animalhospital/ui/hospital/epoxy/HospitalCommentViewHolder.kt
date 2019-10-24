package yhh.dev.animalhospital.ui.hospital.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import yhh.dev.animalhospital.R
import yhh.dev.animalhospital.lib.epoxy.KotlinHolder

@EpoxyModelClass(layout = R.layout.epoxy_view_hospital_comment)
abstract class HospitalCommentViewHolder :
    EpoxyModelWithHolder<HospitalCommentViewHolder.Holder>() {

    @EpoxyAttribute
    lateinit var comment: String

    override fun bind(holder: Holder) {
        holder.comment.text = comment
    }

    class Holder : KotlinHolder() {
        val comment by bind<TextView>(R.id.comment)
    }
}