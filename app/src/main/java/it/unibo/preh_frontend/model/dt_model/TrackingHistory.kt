package it.unibo.preh_frontend.model.dt_model

data class TrackingHistory(
    val crewDeparture: TrackingStep?,
    val arrivalOnsite: TrackingStep?,
    val departureOnsite: TrackingStep?,
    val landingHelipad: TrackingStep?,
    val arrivalEr: TrackingStep?
)