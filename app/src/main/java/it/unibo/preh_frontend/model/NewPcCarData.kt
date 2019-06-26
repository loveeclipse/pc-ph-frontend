package it.unibo.preh_frontend.model

import java.io.Serializable

open class NewPcCarData(
    var event: String,
    var place: String
) : PreHData("NewPcCarData", event), Serializable