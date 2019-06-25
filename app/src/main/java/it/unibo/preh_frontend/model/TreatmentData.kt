package it.unibo.preh_frontend.model

data class TreatmentData(
        var sublussazione: Boolean = false,
        var guedel: Boolean = false,
        var cricoTirotomia: Boolean = false,
        var tuboTracheale: Boolean = false,
        var minitoracotomiaSx: Boolean = false,
        var minitoracotomiaDx: Boolean = false,

        var event: String = "Effettuati Trattamenti"
) : PreHData("TreatmentData", event)