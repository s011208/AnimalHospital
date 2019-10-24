package yhh.dev.animalhospital.ui.hospital.comment

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CommentDialog : DialogFragment() {

    companion object {
        const val EXTRA_COMMENT = "comment"
        const val EXTRA_RATE = "rate"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("yo")
            .setMessage("hi")
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_OK,
                    Intent().also {
                        it.putExtra(
                            EXTRA_COMMENT, "123"
                        )
                        it.putExtra(EXTRA_RATE, 0L)
                    })
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { _, _ ->
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_CANCELED,
                    activity?.intent
                )
            }
            .create()
    }
}