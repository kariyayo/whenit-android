package info.bati11.whenit.ui.event_menu

import androidx.lifecycle.ViewModel
import info.bati11.whenit.domain.Event
import javax.inject.Inject

class EventMenuViewModel @Inject constructor(private val event: Event) : ViewModel() {}
