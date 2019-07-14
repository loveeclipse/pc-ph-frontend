package it.unibo.preh_frontend.model

import it.unibo.preh_frontend.utils.DateManager
import java.util.Date

open class PreHData(
    var type: String,
    val eventName: String,
    val eventTime: Date = DateManager.getCurrentDateTime()
)