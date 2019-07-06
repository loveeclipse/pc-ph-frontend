package it.unibo.preh_frontend.dt_model

data class DtAnagraphicData(val name: String = "",
                            val surname: String = "",
                            val residence: String = "",
                            val birthplace: String = "",
                            val birthday: String = "",
                            val gender: String = "",
                            val anticoagulants: Boolean = false,
                            val antiplatelets: Boolean = false)