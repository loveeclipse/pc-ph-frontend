package it.unibo.preh_frontend.model
import java.io.Serializable

data class VitalParametersData(
    var airways: Int,
    var respiratoryFrequency: Int,
    var periphericalSaturation: Int,
    var cardiacFrequency: Int,
    var beatType: Int,
    var bloodPressure: Int,
    var capillarFillTime: Int,
    var mucousSkinColor: Int,
    var eyesOpening: Int,
    var verbalResponse: Int,
    var motorResponse: Int,
    var pupilSx: Int,
    var pupilDx: Int,
    var photoreagentSx: Boolean,
    var photoreagentDx: Boolean,
    var temperature: Double,

    var event: String = "Inseriti Parametri Vitali"
) : PreHData("VitalParametersData", event), Serializable