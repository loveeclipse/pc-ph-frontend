package it.unibo.preh_frontend.model.dt_model

data class VitalParameters(val respiratoryTract: String,
                           val breathingRate: String,
                           val outlyingSaturationPercentage: Int,
                           val heartbeatRate: Int,
                           val heartbeatType: String,
                           val bloodPressure: Int,
                           val capRefillTime: String,
                           val skinColor: String,
                           val eyeOpening: String,
                           val verbalResponse: String,
                           val motorResponse: String,
                           val leftPupil: String,
                           val rightPupil: String,
                           val leftPhotoReactive: Boolean,
                           val rightPhotoReactive: Boolean,
                           val temperatureInCelsius: Double,
                           val time: String)