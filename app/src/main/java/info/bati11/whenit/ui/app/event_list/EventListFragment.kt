package info.bati11.whenit.ui.app.event_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import info.bati11.whenit.databinding.FragmentEventListBinding
import info.bati11.whenit.ui.ViewModelFactory
import org.threeten.bp.LocalDate
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EventListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: EventListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loadEvents(LocalDate.now())

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
            it?.let {
                adapter.submitList(it)
            }
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

        return binding.root
    }

}
