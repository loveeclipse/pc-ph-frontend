package it.unibo.preh_frontend.model.dt_model

data class PatientData(
    val assignedEvent: String,
    val assignedMission: String,
    val anagraphic: Anagraphic? = null
)