package it.unibo.preh_frontend.model

data class ManeuverData(
    var cervicalCollar: Boolean = false,
    var immobilization: Boolean = false,
    var electricalCardioversion: Boolean = false,
    var gastricProbe: Boolean = false,
    var bladderProbe: Boolean = false,
    var captureFrequency: String = "",
    var amperage: String = "",

    var event: String = "Effettuate Manovre"
) : PreHData("ManeuverData", event)