package it.unibo.preh_frontend.model

data class DrugsData(
    var drugsValue: Int,
        // var crystalloidValue: Int,
    // var succinylcholineValue: Int,
    // var fentanilValue: Int,
    // var ketamineValue: Int,

    var event: String,
    var time: String
) : PreHData("DrugsData", event, time)