package it.unibo.preh_frontend.model

import java.io.Serializable

class IppvData(
    var vt: String,
    var fr: String,
    var peep: String,
    var fio2: String,
    var event: String
) : PreHData("IppvData", event), Serializable