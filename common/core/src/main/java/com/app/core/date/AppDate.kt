package com.app.core.date

import android.util.Log
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*

class AppDate {

    enum class Format(val value:String) {
        YYYY_MM_DD("yyyy-MM-dd")
    }

    fun format(
        date: String,
        fromFormat: Format = Format.YYYY_MM_DD,
        toFormat: Format = Format.YYYY_MM_DD
    ): String {
        if (date.isEmpty()) return ""
        return localDatetime(date, fromFormat).format(DateTimeFormatter.ofPattern(toFormat.value))
    }

    private fun localDatetime(date: String, format: Format = Format.YYYY_MM_DD): LocalDateTime {

        return LocalDateTime.parse(
            date,
            DateTimeFormatter.ofPattern(format.value, Locale.ENGLISH)
        )
    }
    private fun localDate(date: String, format: Format = Format.YYYY_MM_DD): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format.value))
    }

    fun daysBetween(from: String, to: String) : Long{
        val days = ChronoUnit.DAYS.between(localDate(from),localDate(to))
        Log.e("Compare", "workDays: $days")
        return days
    }

    fun currentDate(format: Format= Format.YYYY_MM_DD): String {
        val format = SimpleDateFormat(format.value)
        return format.format(Calendar.getInstance().time);
    }
}