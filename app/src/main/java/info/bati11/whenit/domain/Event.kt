package info.bati11.whenit.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import info.bati11.whenit.domain.EventDate.toEpochDay
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

@VersionedParcelize
data class Event(
    val id: Long?,
    val title: String,
    val year: Int,
    val month: Int,
    val dayOfMonth: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(dayOfMonth)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
