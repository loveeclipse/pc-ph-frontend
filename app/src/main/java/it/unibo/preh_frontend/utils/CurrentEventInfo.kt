package it.unibo.preh_frontend.utils

import it.unibo.preh_frontend.model.dt_model.EventInformation

object CurrentEventInfo {

    var callTime: String = ""
    var address: String = ""
    var notes: String? = ""
    var dispatchCode: String? = ""
    var secondary: Boolean? = false
    var involvedVehicles: Int? = 0
    var dynamic: String? = ""
    var patientsNumber: Int? = 0
    var ongoing: Boolean? = false

    fun set(eventInformation: EventInformation) {
        callTime = eventInformation.callTime
        address = eventInformation.address
        notes = eventInformation.notes
        dispatchCode = eventInformation.dispatchCode
        secondary = eventInformation.secondary
        dynamic = eventInformation.dynamic
        patientsNumber = eventInformation.patientsNumber
        ongoing = eventInformation.ongoing
    }

    fun clear() {
        callTime = ""
        address = ""
        notes = ""
        dispatchCode = ""
        secondary = false
        involvedVehicles = 0
        dynamic = ""
        patientsNumber = 0
        ongoing = false
    }
}