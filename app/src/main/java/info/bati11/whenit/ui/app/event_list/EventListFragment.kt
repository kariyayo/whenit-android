package info.bati11.whenit.ui.app.event_list

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R
import info.bati11.whenit.ui.LicensesActivity
import info.bati11.whenit.ui.SettingsActivity
import info.bati11.whenit.ui.theme.WhenitTheme

@AndroidEntryPoint
class EventListFragment : Fragment(R.layout.fragment_event_list) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WhenitTheme {
                    EventListScreenRoute(
                        onSettingsMenuClick = {
                            startActivity(
                                Intent(requireActivity(), SettingsActivity::class.java),
                                ActivityOptions.makeSceneTransitionAnimation(requireActivity())
                                    .toBundle()
                            )
                        },
                        onLicensesMenuClick = {
                            startActivity(
                                Intent(requireActivity(), LicensesActivity::class.java),
                                ActivityOptions.makeSceneTransitionAnimation(requireActivity())
                                    .toBundle()
                            )
                        },
                        navController = findNavController(),
                    )
                }
            }
        }
    }
}
