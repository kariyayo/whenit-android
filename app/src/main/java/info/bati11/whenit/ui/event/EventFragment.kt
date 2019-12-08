package info.bati11.whenit.ui.event


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

import info.bati11.whenit.R

/**
 * A simple [Fragment] subclass.
 */
class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.fab)
        floatingActionButton.setOnClickListener {
            view.findNavController().navigate(EventFragmentDirections.actionEventFragmentToEventCreateFragment())
        }
        return view
    }

}
