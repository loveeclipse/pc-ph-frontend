package it.unibo.preh_frontend.utils

object DtIdentifiers {

    // UUID's
    var assignedEvent: String? = null
    var assignedMission: String? = null
    var patientId: String? = null

    var vehicle: String? = null
    var doctor: String? = null

    fun clear() {
        assignedEvent = null
        assignedMission = null
        patientId = null
        vehicle = null
        doctor = null
    }
}