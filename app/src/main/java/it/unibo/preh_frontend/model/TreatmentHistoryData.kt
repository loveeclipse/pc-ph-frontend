package it.unibo.preh_frontend.model

data class TreatmentHistoryData(
    var treatmentBooleanValue: Boolean?,
    var treatmentStringValue: String?,
    var event: String
) : PreHData("TreatmentHistoryData", event)