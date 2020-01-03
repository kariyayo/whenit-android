package info.bati11.whenit.domain

import info.bati11.whenit.domain.EventDate.toEpochDay
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

data class Event(val id: Long?, val title: String, val year: Int, val month: Int, val dayOfMonth: Int) {

    fun years(now: Long): Long {
        val localDate = LocalDate.of(year, month, dayOfMonth)
        val today = LocalDate.ofEpochDay(toEpochDay(now))
        return ChronoUnit.YEARS.between(localDate, today)
    }

    fun daysOfYears(now: Long): Long {
        val localDate = LocalDate.of(year, month, dayOfMonth)
        val today = LocalDate.ofEpochDay(toEpochDay(now))
        val days = ChronoUnit.DAYS.between(localDate, today)
        val years = ChronoUnit.YEARS.between(localDate, today)
        return days - (years * 365)
    }
}
