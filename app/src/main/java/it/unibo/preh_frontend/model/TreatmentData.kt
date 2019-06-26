package it.unibo.preh_frontend.model

data class TreatmentData(
    var subluxation: Boolean = false,
    var guedel: Boolean = false,
    var cricoTirotomia: Boolean = false,
    var trachealTube: Boolean = false,
    var minitoracotomiaSx: Boolean = false,
    var minitoracotomiaDx: Boolean = false,
    var event: String = "Effettuati Trattamenti"
) : PreHData("TreatmentData", event)