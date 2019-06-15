package it.unibo.preh_frontend.model

data class AnagraphicData(
    var name: String = "",
    var surname: String = "",
    var residence: String = "",
    var birthplace: String = "",
    var birthday: String = "",
    var gender: Int = -1,
    var anticoagulants: Boolean = false,
    var antiplatelets: Boolean = false
) : PreHData {
    override fun getDataAsString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}