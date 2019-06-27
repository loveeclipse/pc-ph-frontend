package it.unibo.preh_frontend.model

import java.io.Serializable

data class PatientStatusData(
    var closedTrauma: Boolean = false,
    var piercingTrauma: Boolean = false,
    var helmetBelt: Boolean,
    var hemorrage: Boolean = false,
    var airways: Boolean = false,
    var tachipnea: Boolean = false,
    var costalVolet: Boolean = false,
    var ecofastPositive: Boolean = false,
    var ecofastNegative: Boolean = false,
    var pelvisStatus: Boolean = false,
    var amputation: Boolean = false,
    var sunkenSkull: Boolean = false,
    var otorrhagia: Boolean = false,
    var paraparesis: Boolean = false,
    var tetraparesis: Boolean = false,
    var paresthesia: Boolean = false,
    var physiologicCriterion: Boolean = false,
    var anatomicCriterion: Boolean = false,
    var dynamicCriterion: Boolean = false,
    var clinicalJudgement: Boolean = false,
    var shockIndex: Double = 0.0,

    var event: String = "Modificato Stato Paziente"
) : PreHData("PatientStatusData", event), Serializable