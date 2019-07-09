package it.unibo.preh_frontend.model.dt_model

data class Anagraphic(
    val name: String,
    val surname: String,
    val residency: String,
    val birthPlace: String,
    val birthDate: String,
    val gender: String,
    val anticoagulants: Boolean,
    val antiplatelets: Boolean
)