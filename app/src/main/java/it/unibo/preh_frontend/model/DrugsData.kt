package it.unibo.preh_frontend.model

data class DrugsData(
    var crystalloidValue: Int,
    var succinylcholineValue: Int,
    var fentanilValue: Int,
    var ketamineValue: Int
) : PreHData()