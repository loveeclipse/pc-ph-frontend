package it.unibo.preh_frontend.model

data class ManeuverData(
    var cervicalCollar: Boolean,
    var immobilization: Boolean,
    var electricalCardioversion: Boolean,
    var gastricProbe: Boolean,
    var bladderProbe: Boolean,
    var event: String = "Effettuate Manovre"
) : PreHData("ManeuverData", event)