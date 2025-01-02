package info.bati11.whenit.domain

import android.os.Parcelable
import info.bati11.whenit.domain.EventDate.toEpochDay
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

@Parcelize
data class Event(
    val id: Long?,
    val title: String,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int
) : Parcelable {

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

    fun epochMilliSeconds(): Long {
        val localDate = LocalDate.of(year, month, dayOfMonth)
        return localDate.toEpochDay() * 24 * 60 * 60 * 1000
    }
}
