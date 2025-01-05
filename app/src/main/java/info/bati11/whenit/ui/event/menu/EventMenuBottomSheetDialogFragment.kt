package info.bati11.whenit.ui.event.menu

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R

@AndroidEntryPoint
class EventMenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: EventMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true

        val event =
            EventMenuBottomSheetDialogFragmentArgs.fromBundle(
                requireArguments()
            ).event
        viewModel.init(event)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_title_delete_confirm))
            .setMessage(getString(R.string.dialog_text_text_delete_confirm_dialog))
            .setPositiveButton(R.string.label_yes) { dialog, which ->
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    viewModel.deleteEvent()
                }
                dialog.dismiss()
            }
            .setNegativeButton(R.string.label_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
