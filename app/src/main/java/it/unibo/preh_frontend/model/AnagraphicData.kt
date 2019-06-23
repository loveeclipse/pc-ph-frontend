package it.unibo.preh_frontend.model

data class AnagraphicData(
    var name: String = "",
    var surname: String = "",
    var residence: String = "",
    var birthplace: String = "",
    var birthday: String = "",
    var gender: Int = -1,
    var anticoagulants: Boolean = false,
    var antiplatelets: Boolean = false,

    var event: String = "Modificati Dati Anagrafici"
) : PreHData("AnagraphicData", event)