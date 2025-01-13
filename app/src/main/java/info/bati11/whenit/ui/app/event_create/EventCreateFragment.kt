package info.bati11.whenit.ui.app.event_create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.ui.theme.WhenitTheme

@AndroidEntryPoint
class EventCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WhenitTheme {
                    EventCreateScreenRoute(
                        navController = findNavController(),
                        onClickDateField = { focusState, viewModel ->
                            if (focusState.hasFocus) {
                                val datePicker = initDatePicker(viewModel)
                                datePicker.show(parentFragmentManager, "datePicker")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun initDatePicker(
        viewModel: EventCreateViewModel
    ): MaterialDatePicker<Long> {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.addOnPositiveButtonClickListener { selection ->
            viewModel.inputDate(selection)
        }
        return picker
    }
}
