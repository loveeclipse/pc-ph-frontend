package it.unibo.preh_frontend.model

data class ComplicationsHistoryData(
    var complicationsValue: Boolean,
    var event: String
) : PreHData("ComplicationsHistoryData", event)