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
import dagger.android.support.DaggerFragment
import info.bati11.whenit.databinding.FragmentEventCreateBinding
import info.bati11.whenit.ui.ViewModelFactory
import info.bati11.whenit.ui.afterTextChanged
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EventCreateFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventCreateViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventCreateBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // navigation
        viewModel.navigateToEvent.observe(this, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(EventCreateFragmentDirections.actionEventCreateFragmentToEventFragment())
                viewModel.onNavigatedToEvent()
            }
        })

        // titleEditText
        binding.titleEditText.afterTextChanged { viewModel.inputTitle(it) }
        viewModel.formTitleErrMsg.observe(this, Observer { binding.titleEditTextLayout.error = it })

        // dateEditText
        binding.dateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.onDateEditTextClicked()
        }
        viewModel.formDateErrMsg.observe(this, Observer { binding.dateEditTextLayout.error = it })
        val datePicker = initDatePicker(binding, viewModel)
        viewModel.showDatePickerDialogEvent.observe(this, Observer { show ->
            if (show) datePicker?.show(fragmentManager!!, "datePicker")
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
