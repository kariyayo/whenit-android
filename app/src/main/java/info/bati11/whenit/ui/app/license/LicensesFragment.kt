package info.bati11.whenit.ui.app.license

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R
import info.bati11.whenit.ui.app.setting.SettingsRoute
import info.bati11.whenit.ui.theme.WhenitTheme

@AndroidEntryPoint
class LicensesFragment : Fragment(R.layout.fragment_licenses) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WhenitTheme {
                    LicensesRoute(
                        navController = findNavController(),
                    )
                }
            }
        }
    }
}