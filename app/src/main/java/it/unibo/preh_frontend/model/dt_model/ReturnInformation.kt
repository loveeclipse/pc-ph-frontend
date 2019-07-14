package it.unibo.preh_frontend.model.dt_model

data class ReturnInformation(
    val returnCode: Int,
    val returnHospital: String,
    val emergencyRoom: String?
)