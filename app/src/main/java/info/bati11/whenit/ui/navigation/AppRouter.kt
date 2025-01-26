package info.bati11.whenit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import info.bati11.whenit.ui.app.event_create.EventCreateScreen
import info.bati11.whenit.ui.app.event_list.EventListScreen
import info.bati11.whenit.ui.app.license.LicensesScreen
import info.bati11.whenit.ui.app.setting.SettingsScreen

@Composable
fun AppRouter() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.EventList,
    ) {
        composable<Route.Settings> {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.Licenses> {
            LicensesScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.EventList> {
            EventListScreen(
                onSettingsMenuClick = {
                    navController.navigate(Route.Settings)
                },
                onLicensesMenuClick = {
                    navController.navigate(Route.Licenses)
                },
                onEventCreateClick = {
                    navController.navigate(Route.EventCreate)
                },
            )
        }
        composable<Route.EventCreate> {
            EventCreateScreen(
                onClose = {
                    navController.popBackStack()
                },
                onCompleted = {
                    navController.popBackStack(Route.EventList, inclusive = false)
                },
            )
        }
    }
}
