package it.unibo.preh_frontend.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

data class NewPcCarData(
    var event: String,
    var place: String,
    var time: String = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
) : PreHData("NewPcCarData", event), Serializable