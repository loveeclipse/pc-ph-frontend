package it.unibo.preh_frontend.model

data class TreatmentData(
    var resuscitation: Boolean = false,
    var jawSubluxation: Boolean = false,
    var guedel: Boolean = false,
    var cricothyrotomy: Boolean = false,
    var trachealTube: Boolean = false,
    var minithoracotomySx: Boolean = false,
    var minithoracotomyDx: Boolean = false,
    var tourniquet: Boolean = false,
    var reboaArea1: Boolean = false,
    var reboaArea3: Boolean = false,
    var event: String = "Effettuati Trattamenti"
) : PreHData("TreatmentData", event)