package it.unibo.preh_frontend.model

data class ManeuverHistoryData(
    var maneuverValue: Boolean,
    var event: String
) : PreHData("ManeuverData", event)