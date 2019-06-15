package it.unibo.preh_frontend.model

data class TreatmentData(
    var sublussazione: Boolean = false,
    var guedel: Boolean = false,
    var cricoTirotomia: Boolean = false,
    var tuboTrachealer: Boolean = false,
    var minitoracotomiaSx: Boolean = false,
    var minitoracotomiaDx: Boolean = false
) : PreHData // Aggiungere IPPV values?
{
    override fun getDataAsString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}