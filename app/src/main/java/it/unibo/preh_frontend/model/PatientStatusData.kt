package it.unibo.preh_frontend.model

import java.io.Serializable

data class PatientStatusData(
    var traumaChiuso: Boolean = false,
    var traumaPenetrante: Boolean = false,
    var cascoCintura: Boolean = true,
    var emorragia: Boolean = false,
    var vieAeree: Boolean = false,
    var tachipnea: Boolean = false,
    var voletCostale: Boolean = false,
    var ecofast: Boolean = false,
    var bacinoStatus: Boolean = false,
    var amputazione: Boolean = false,
    var fratturaCranica: Boolean = false,
    var paraparesi: Boolean = false,
    var tetraparesi: Boolean = false,
    var parestesia: Boolean = false,
    var criterioFisiologico: Boolean = false,
    var criterioAnatomico: Boolean = false,
    var criterioDinamico: Boolean = false,
    var shockIndex: Boolean = false,

    var event: String = "Modificato Stato Paziente",
    var time: String
) : PreHData("PatientStatusData", event, time),Serializable