package it.unibo.preh_frontend.model

open class HistoryData(
    val type: String,
    val event: String,
    val eventData: PreHData,
    val eventTime: String
)

internal class HistoryAnagraphicData(
    event: String,
    eventData: AnagraphicData,
    eventTime: String
) : HistoryData("AnagraphicData", event, eventData, eventTime)

internal class HistoryComplicationsData(
    event: String,
    eventData: ComplicationsData,
    eventTime: String
) : HistoryData("ComplicationsData", event, eventData, eventTime)

internal class HistoryManeuverData(
    event: String,
    eventData: ManeuverData,
    eventTime: String
) : HistoryData("ManeuverData", event, eventData, eventTime)

internal class HistoryTreatmentData(
    event: String,
    eventData: TreatmentData,
    eventTime: String
) : HistoryData("TreatmentData", event, eventData, eventTime)

internal class HistoryPatientStatusData(
    event: String,
    eventData: PatientStatusData,
    eventTime: String
) : HistoryData("PatientStatusData", event, eventData, eventTime)

internal class HistoryVitalParametersData(
    event: String,
    eventData: VitalParametersData,
    eventTime: String
) : HistoryData("VitalParametersData", event, eventData, eventTime)
