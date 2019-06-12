package it.unibo.preh_frontend.model

class PatientStatusData(var traumaType: Boolean = false,
                        var cascoCintura: Boolean = true,
                        var emorragia: Boolean = false,
                        var vieAeree: Boolean= false,
                        var tachipnea: Boolean= false,
                        var voletCostale: Boolean= false,
                        var ecofast: Boolean= false,
                        var bacinoStatus: Boolean= false,
                        var amputazione: Boolean= false,
                        var fratturaCranica: Boolean= false,
                        var paraparesi: Boolean= false,
                        var tetraparesi: Boolean= false,
                        var parestesia: Boolean= false,
                        var criterioFisiologico: Boolean= false,
                        var cariterioAnatomico: Boolean= false,
                        var criterioDinamico: Boolean= false,
                        var shockIndex: Boolean= false){


    override fun toString(): String {
        return "$traumaType  $cascoCintura"
    }
}