package it.unibo.preh_frontend.model

import java.io.Serializable

data class DrugsData(
    var drugsValue: Int,
    var unitOfMeasure: String,
    var event: String
) : PreHData("DrugsData", event) , Serializable