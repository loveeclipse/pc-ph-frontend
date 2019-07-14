package it.unibo.preh_frontend.model.dt_model

data class PatientStatus(
    val traumaticCondition: Boolean,
    val closedTrauma: Boolean,
    val penetratingTrauma: Boolean,
    val helmetSeatbelt: Boolean,
    val externalHaemorrhage: Boolean,
    val respiratoryTract: Boolean,
    val tachypneaDyspnoea: Boolean,
    val thoraxDeformities: Boolean,
    val ecofast: Boolean,
    val deformedPelvis: Boolean,
    val amputation: Boolean,
    val sunkenSkullFracture: Boolean,
    val otorrhagia: Boolean,
    val paraparesis: Boolean,
    val tetraparesis: Boolean,
    val paraesthesia: Boolean,
    val time: String
)