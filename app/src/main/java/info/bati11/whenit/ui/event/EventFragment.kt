package info.bati11.whenit.ui.event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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

        val viewModel = ViewModelProviders.of(this, EventViewModel.Factory(activity!!.application)).get(EventViewModel::class.java)
        binding.viewModel = viewModel

        binding.fab.setOnClickListener {
            findNavController().navigate(EventFragmentDirections.actionEventFragmentToEventCreateFragment())
        }

        return binding.root
    }

}
