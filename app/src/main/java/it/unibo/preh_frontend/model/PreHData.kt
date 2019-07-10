package it.unibo.preh_frontend.model

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

open class PreHData(
    var type: String,
    val eventName: String,
    val eventTime: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
)