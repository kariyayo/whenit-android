package info.bati11.whenit.ui.event.menu

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.bati11.whenit.App
import info.bati11.whenit.databinding.FragmentBottomSheetEventMenuBinding
import info.bati11.whenit.ui.ViewModelFactory
import timber.log.Timber
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
