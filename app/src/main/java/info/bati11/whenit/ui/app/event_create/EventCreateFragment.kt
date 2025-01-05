package info.bati11.whenit.ui.app.event_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R
import info.bati11.whenit.databinding.FragmentEventCreateBinding
import info.bati11.whenit.ui.afterTextChanged
import info.bati11.whenit.ui.hideSoftwareKeyboard

@AndroidEntryPoint
class EventCreateFragment : Fragment(R.layout.fragment_event_create) {

    private val viewModel: EventCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventCreateBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // navigation
        viewModel.navigateToEvent.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                binding.titleEditText.hideSoftwareKeyboard(requireActivity())
                findNavController().navigate(EventCreateFragmentDirections.actionEventCreateFragmentToEventFragment())
                viewModel.onNavigatedToEvent()
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

        return binding.root
    }

    private fun initDatePicker(
        binding: FragmentEventCreateBinding,
        viewModel: EventCreateViewModel
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
