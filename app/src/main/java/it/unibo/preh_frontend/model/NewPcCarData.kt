package it.unibo.preh_frontend.model

import java.io.Serializable

open class NewPcCarData(
    var event: String,
    var place: String,
    typeName: String = "NewPcCarData"
) : PreHData(typeName, event), Serializable