package it.unibo.preh_frontend.model.dt_model

data class MissionInformation(
    val eventId: String,
    val vehicle: String,
    val returnInformation: ReturnInformation?,
    val tracking: TrackingHistory?,
    val ongoing: Boolean
)