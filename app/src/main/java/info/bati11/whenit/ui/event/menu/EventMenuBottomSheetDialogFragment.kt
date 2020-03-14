package info.bati11.whenit.ui.event.menu

import android.app.Dialog
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.bati11.whenit.Application
import info.bati11.whenit.databinding.FragmentBottomSheetEventMenuBinding
import timber.log.Timber

class EventMenuBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog, style: Int) {
        val binding = FragmentBottomSheetEventMenuBinding.inflate(dialog.layoutInflater)

        val event =
            EventMenuBottomSheetDialogFragmentArgs.fromBundle(
                arguments!!
            ).event

        val viewModelFactory =
            (activity!!.application as Application)
                .appComponent
                .eventMenuComponent()
                .create(event)
                .viewModelFactory()
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EventMenuViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToEventEdit.observe(this, Observer {
            if (it) {
                Timber.i("navigate to event edit")
                Toast.makeText(requireContext(), "navigate to event edit", Toast.LENGTH_LONG).show()
                viewModel.onNavigatedToEventEdit()
            }
        })

        viewModel.navigateToEventRemove.observe(this, Observer {
            if (it) {
                Timber.i("navigate to event remove")
                Toast.makeText(requireContext(), "navigate to event remove", Toast.LENGTH_LONG).show()
                viewModel.onNavigatedToEventRemove()
            }
        })

        dialog.setContentView(binding.root)
    }
}
