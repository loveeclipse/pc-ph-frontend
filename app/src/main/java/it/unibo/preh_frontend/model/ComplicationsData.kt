package it.unibo.preh_frontend.model

data class ComplicationsData(
    var cardioCirculatoryArrest: Boolean = false,
    var deterioratingStateConsciousness: Boolean = false,
    var anisoMidriasi: Boolean = false,
    var respiratoryFailure: Boolean = false,
    var cardioCirculatoryShock: Boolean = false,
    var landingInItinere: Boolean = false,
    var deathInItinere: Boolean = false,
    var deathInPs: Boolean = false,
    var event: String = "Modificate Complicazioni"
) : PreHData("ComplicationsData", event)