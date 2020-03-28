package info.bati11.whenit.ui.event.menu

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import info.bati11.whenit.App
import info.bati11.whenit.R
import info.bati11.whenit.databinding.FragmentBottomSheetEventMenuBinding
import info.bati11.whenit.ui.ViewModelFactory
import javax.inject.Inject

class EventMenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventMenuViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        val event =
            EventMenuBottomSheetDialogFragmentArgs.fromBundle(
                arguments!!
            ).event
        val eventComponent = (activity!!.application as App).appComponent
            .eventMenuComponent()
            .create(event)
        eventComponent.inject(this)

        super.onAttach(context)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        val binding = FragmentBottomSheetEventMenuBinding.inflate(dialog.layoutInflater)
        binding.viewModel = viewModel

        viewModel.navigateToEventEdit.observe(this, Observer {
            if (it) {
                findNavController().navigate(
                    EventMenuBottomSheetDialogFragmentDirections.actionEventMenuBottomSheetDialogToEventEditFragment(
                        viewModel.event
                    )
                )
                viewModel.onNavigatedToEventEdit()
            }
        })

        viewModel.showDeleteConfirmDialog.observe(this, Observer {
            if (it) {
                MaterialAlertDialogBuilder(context)
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
        })

        viewModel.doneEventDelete.observe(this, Observer { isDone ->
            if (isDone) {
                findNavController().popBackStack()
            }
        })

        dialog.setContentView(binding.root)
    }
}
