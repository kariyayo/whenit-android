package info.bati11.whenit.notifications.reminder

import info.bati11.whenit.domain.Event
import info.bati11.whenit.repository.EventRepository
import org.threeten.bp.LocalDate

class RemindMessageFactory constructor(
    private val eventRepository: EventRepository,
    private val titleString: String = "Whenit",
    private val prefixStringTomorrow: String = "tomorrow is",
    private val prefixStringNextWeek: String = "one week later,",
    private val prefixStringNextMonth: String = "one month later,",
    private val contentString: String = "%s %s",
    private val suffixString: String = " .",
    private val suffixStringOthers: String = " and others."
) {
    fun createRemindContent(
        date: LocalDate,
        isAvailableDay: Boolean,
        isAvailableWeek: Boolean,
        isAvailableMonth: Boolean
    ): Pair<String, String>? {
        val content = mutableListOf<String>()
        if (isAvailableDay) {
            val events = eventRepository.findByDate(date.plusDays(1))
            val s = convertToString(events, prefixStringTomorrow)
            if (s.isNotBlank()) content.add(s)
        }

        if (isAvailableWeek) {
            val events = eventRepository.findByDate(date.plusWeeks(1))
            val s = convertToString(events, prefixStringNextWeek)
            if (s.isNotBlank()) content.add(s)
        }

        if (isAvailableMonth) {
            val events = eventRepository.findByDate(date.plusMonths(1))
            val s =convertToString(events, prefixStringNextMonth)
            if (s.isNotBlank()) content.add(s)
        }

        return if (content.isNotEmpty()) Pair(titleString, content.joinToString("\n")) else null
    }

    private fun convertToString(events: List<Event>, prefix: String) : String {
        val result = StringBuilder()
        events.firstOrNull()?.let {
            result.append(contentString.format(prefix, it.title))
            val others = events.drop(1).size
            if (others > 0) {
                result.append(suffixStringOthers.format(others))
            } else {
                result.append(suffixString.format(others))
            }
        }
        return result.toString()
    }
}
