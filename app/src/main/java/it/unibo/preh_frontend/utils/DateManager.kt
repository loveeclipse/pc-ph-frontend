package it.unibo.preh_frontend.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import java.util.Locale

object DateManager {

    fun getCurrentDateTime(): Date = Calendar.getInstance().time

    fun getHistoryRepresentation(date: Date = getCurrentDateTime()): String =
            SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(date)

    fun getStandardRepresentation(date: Date = getCurrentDateTime()): String =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(date)
}