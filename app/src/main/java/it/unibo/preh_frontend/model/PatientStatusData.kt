package it.unibo.preh_frontend.model

import java.io.Serializable

data class PatientStatusData(
        var closedTrauma: Boolean = false,
        var piercingTrauma: Boolean = false,
        var helmetBelt: Boolean = true,
        var hemorrage: Boolean = false,
        var airways: Boolean = false,
        var tachipnea: Boolean = false,
        var costalVolet: Boolean = false,
        var ecofast: Boolean = false,
        var pelvisStatus: Boolean = false,
        var amputation: Boolean = false,
        var cranialFracture: Boolean = false,
        var paraparesis: Boolean = false,
        var tetraparesis: Boolean = false,
        var paresthesia: Boolean = false,
        var physiologicCriterion: Boolean = false,
        var anatomicCriterion: Boolean = false,
        var dynamicCriterion: Boolean = false,
        var shockIndex: Boolean = false,

        var event: String = "Modificato Stato Paziente"
) : PreHData("PatientStatusData", event), Serializable