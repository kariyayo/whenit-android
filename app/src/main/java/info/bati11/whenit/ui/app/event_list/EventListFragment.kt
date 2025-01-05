package info.bati11.whenit.ui.app.event_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R
import info.bati11.whenit.databinding.FragmentEventListBinding

@AndroidEntryPoint
class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private val viewModel: EventListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToEventCreate.observe(viewLifecycleOwner, Observer {
            if (it) {
                val navController = findNavController()
                navController.navigate(EventListFragmentDirections.actionEventFragmentToEventCreateFragment())
                viewModel.onNavigatedToEventCreate()
            }
        })

        val adapter =
            EventListAdapter(EventMenuClickListener { event ->
                viewModel.displayEventMenu(event)
            })
        binding.eventList.adapter = adapter
        viewModel.events.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.showSelectedEventMenu.observe(viewLifecycleOwner, Observer { event ->
            event?.let {
                val navController = findNavController()
                navController.navigate(
                    EventListFragmentDirections.actionEventFragmentToEventMenuBottomSheetDialog(
                        it
                    )
                )
                viewModel.displayEventMenuComplete()
            }
        })

        val appCompatActivity: AppCompatActivity? = (activity as AppCompatActivity?)
        appCompatActivity?.supportActionBar?.show()

        binding.composeView.setContent {
            SampleContent()
        }

        return binding.root
    }

}

@Composable
fun SampleContent() {
    Text("Foo Bar")
}
