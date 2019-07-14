package it.unibo.preh_frontend.model.dt_model

data class EventInformation(
    val callTime: String,
    val address: String,
    val notes: String?,
    val dispatchCode: String?,
    val secondary: Boolean?,
    val dynamic: String?,
    val patientsNumber: Int?,
    val ongoing: Boolean?
)