package it.unibo.preh_frontend.model


open class HistoryData<PreHData>(
        val type: String,
        val event: String,
        val eventData: PreHData,
        val eventTime: String
)

internal class HistoryAnagraphicData<AnagraphicData>(event: String,
                                                     eventData: AnagraphicData,
                                                     eventTime: String
) : HistoryData<AnagraphicData>("AnagraphicData",event,eventData,eventTime)

internal class HistoryComplicationsData<ComplicationsData>(event: String,
                                                           eventData: ComplicationsData,
                                                           eventTime: String
) : HistoryData<ComplicationsData>("ComplicationsData",event,eventData,eventTime)

internal class HistoryManeuverData<ManeuverData>(event: String,
                                                 eventData: ManeuverData,
                                                 eventTime: String
) : HistoryData<ManeuverData>("ManeuverData",event,eventData,eventTime)

internal class HistoryTreatmentData<TreatmentData>(event: String,
                                                 eventData: TreatmentData,
                                                 eventTime: String
) : HistoryData<TreatmentData>("TreatmentData",event,eventData,eventTime)

internal class HistoryPatientStatusData<PatientStatusData>(event: String,
                                                 eventData: PatientStatusData,
                                                 eventTime: String
) : HistoryData<PatientStatusData>("PatientStatusData",event,eventData,eventTime)

internal class HistoryVitalParametersData<VitalParametersData>(event: String,
                                                 eventData: VitalParametersData,
                                                 eventTime: String
) : HistoryData<VitalParametersData>("VitalParametersData",event,eventData,eventTime)

