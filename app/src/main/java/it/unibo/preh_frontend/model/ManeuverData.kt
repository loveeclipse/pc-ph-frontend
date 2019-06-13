package it.unibo.preh_frontend.model

class ManeuverData(
    var collare: Boolean = false,
    var immobilizzazione: Boolean = false,
    var cardioversione: Boolean = false,
    var sondaGastrica: Boolean = false,
    var sondaVescicale: Boolean = false,
    var freqCattura: String = "",
    var amperaggio: String = ""
)