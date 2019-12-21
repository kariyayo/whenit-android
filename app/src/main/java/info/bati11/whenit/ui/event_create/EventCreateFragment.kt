package info.bati11.whenit.ui.event_create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import info.bati11.whenit.databinding.FragmentEventCreateBinding

/**
 * A simple [Fragment] subclass.
 */
class EventCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentEventCreateBinding.inflate(inflater).root
    }

}
