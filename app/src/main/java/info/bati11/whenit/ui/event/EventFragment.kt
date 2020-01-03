package info.bati11.whenit.ui.event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import info.bati11.whenit.Application
import info.bati11.whenit.databinding.FragmentEventBinding

/**
 * A simple [Fragment] subclass.
 */
class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory =
            (activity!!.application as Application).appComponent.viewModelFactory()
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToEventCreate.observe(viewLifecycleOwner, Observer {
            if (it) {
                val navController = findNavController()
                navController.navigate(EventFragmentDirections.actionEventFragmentToEventCreateFragment())
                viewModel.onNavigatedToEventCreate()
            }
        })

        val appCompatActivity: AppCompatActivity? = (activity as AppCompatActivity?)
        appCompatActivity?.supportActionBar?.show()

        return binding.root
    }

}
