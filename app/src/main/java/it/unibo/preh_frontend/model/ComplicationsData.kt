package it.unibo.preh_frontend.model

data class ComplicationsData(
    var arrestoCardiocircolatorio: Boolean = false,
    var deterioramento: Boolean = false,
    var anisocoria: Boolean = false,
    var insuffRespiratoria: Boolean = false,
    var cardioShock: Boolean = false,
    var atterraggio: Boolean = false,
    var itinere: Boolean = false,
    var arrivoPs: Boolean = false
) : PreHData()