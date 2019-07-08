package it.unibo.preh_frontend.model.dt_model

data class AnagraphicData(val name: String = "",
                          val surname: String = "",
                          val residence: String = "",
                          val birthplace: String = "",
                          val birthday: String = "",
                          val gender: String = "",
                          val anticoagulants: Boolean = false,
                          val antiplatelets: Boolean = false)