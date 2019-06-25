package it.unibo.preh_frontend.model

data class DrugsData(
    var drugsValue: Int,
    var unitOfMeasure: String,
    var event: String
) : PreHData("DrugsData", event)