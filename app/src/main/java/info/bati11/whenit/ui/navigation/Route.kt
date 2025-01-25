package info.bati11.whenit.ui.navigation

import kotlinx.serialization.Serializable

object Route {

    @Serializable
    data object Settings

    @Serializable
    data object Licenses

    @Serializable
    data object EventList

    @Serializable
    data object EventCreate
}
