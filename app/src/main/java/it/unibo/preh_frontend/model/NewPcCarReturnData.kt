package it.unibo.preh_frontend.model

import java.io.Serializable

data class NewPcCarReturnData(
    var event: String,
    var place: String,
    var returnCode: Int,
    var hospital: Int,
    var vehicle: Int,
    var accompanyingMedic: Boolean
) : PreHData("NewPcCarReturnData", event), Serializable