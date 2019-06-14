package it.unibo.preh_frontend.model

data class VitalParametersData(
    var vieAeree: Int,
    var frequenzaRespiratoria: Int,
    var saturazionePeriferica: Int,
    var frequenzaCaridaca: Int,
    var tipoBattito: Int,
    var pressioneArteriosa: Int,
    var tempoRiempimentoCapillare: Int,
    var coloritoCuteMucose: Int,
    var aperturaOcchi: Int,
    var rispostaVerbale: Int,
    var rispostaMotoria: Int,
    var pupilleSx: Int,
    var pupilleDx: Int,
    var fotoreagenteSx: Boolean,
    var fotoreagenteDx: Boolean,
    var temperature: Int
)