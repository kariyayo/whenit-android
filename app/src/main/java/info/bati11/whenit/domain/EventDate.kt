package info.bati11.whenit.domain

import org.threeten.bp.LocalDate

object EventDate {
    fun toEpochDay(epochTime: Long): Long = epochTime / 1000 / 60 / 60 / 24
    fun toLocalDate(epochTime: Long): LocalDate = LocalDate.ofEpochDay(toEpochDay(epochTime))
}