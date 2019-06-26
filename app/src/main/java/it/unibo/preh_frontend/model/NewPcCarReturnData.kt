package it.unibo.preh_frontend.model

data class NewPcCarReturnData(
    var eventN: String,
    var eventPlace: String,
    var returnCode: Int,
    var hospital: String,
    var accompanyingMedic: Boolean
) : NewPcCarData(eventN, eventPlace, "NewPcCarReturnData")