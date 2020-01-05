package info.bati11.whenit.ui.event_menu

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.bati11.whenit.Application
import info.bati11.whenit.databinding.FragmentBottomSheetEventMenuBinding

class EventMenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val binding = FragmentBottomSheetEventMenuBinding.inflate(dialog.layoutInflater)

        val event =
            EventMenuBottomSheetDialogFragmentArgs.fromBundle(arguments!!).event

        val viewModelFactory =
            (activity!!.application as Application)
                .appComponent
                .eventMenuComponent()
                .create(event)
                .viewModelFactory()
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EventMenuViewModel::class.java)
        binding.viewModel = viewModel

        dialog.setContentView(binding.root)
    }
}
