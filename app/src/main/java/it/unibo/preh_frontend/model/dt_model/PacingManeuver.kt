package it.unibo.preh_frontend.model.dt_model

data class PacingManeuver(
    val captureRateInBpm: Int,
    val amperageInMilliAmps: Int,
    val time: String
)