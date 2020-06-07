package info.bati11.whenit.ui.event.edit

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import info.bati11.whenit.App
import info.bati11.whenit.R
import info.bati11.whenit.databinding.FragmentEventEditBinding
import info.bati11.whenit.ui.ViewModelFactory
import info.bati11.whenit.ui.afterTextChanged
import info.bati11.whenit.ui.hideSoftwareKeyboard
import javax.inject.Inject

class EventEditFragment : Fragment(R.layout.fragment_event_edit) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventEditViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        val event = EventEditFragmentArgs.fromBundle(requireArguments()).event
        val eventComponent = (requireActivity().application as App).appComponent
            .eventMenuComponent()
            .create(event)
        eventComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEventEditBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // navigation
        viewModel.popBack.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.titleEditText.hideSoftwareKeyboard(requireActivity())
                findNavController().popBackStack()
                viewModel.onPopBacked()
            }
        })

        // titleEditText
        binding.titleEditText.afterTextChanged { viewModel.inputTitle(it) }
        viewModel.formTitleErr.observe(
            viewLifecycleOwner,
            Observer {
                binding.titleEditTextLayout.error = if (it != null) {
                    getString(R.string.input_helper_text_required)
                } else {
                    null
                }
            })

        // dateEditText
        binding.dateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.onDateEditTextClicked()
        }
        viewModel.formDateErr.observe(
            viewLifecycleOwner,
            Observer {
                binding.dateEditTextLayout.error = if (it != null) {
                    getString(R.string.input_helper_text_required)
                } else {
                    null
                }
            })
        val datePicker = initDatePicker(binding, viewModel)
        viewModel.showDatePickerDialogEvent.observe(viewLifecycleOwner, Observer { show ->
            if (show) datePicker.show(parentFragmentManager, "datePicker")
        })

        // actionBar
        val appCompatActivity: AppCompatActivity? = (activity as AppCompatActivity?)
        appCompatActivity?.supportActionBar?.hide()
    }

    private fun initDatePicker(
        binding: FragmentEventEditBinding,
        viewModel: EventEditViewModel
    ): MaterialDatePicker<Long> {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.addOnCancelListener {
            binding.dateEditText.clearFocus()
            viewModel.onDismissDatePicker()
        }
        picker.addOnDismissListener {
            binding.dateEditText.clearFocus()
            viewModel.onDismissDatePicker()
        }
        picker.addOnPositiveButtonClickListener { selection ->
            viewModel.inputDate(selection)
        }
        return picker
    }
}
