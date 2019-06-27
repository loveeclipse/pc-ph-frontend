package it.unibo.preh_frontend.model

data class TerminatePreHData(
    var returnCode: Int,
    var hospital: Int,
    var hospitalPlace: Int,
    var terminate: Boolean,
    var event: String = "Terminato pre-h"
) : PreHData("TerminatePreHData", event)