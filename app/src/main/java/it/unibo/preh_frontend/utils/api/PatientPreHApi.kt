package it.unibo.preh_frontend.utils.api


import it.unibo.preh_frontend.dt_model.PatientData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PatientPreHApi {
    @POST("patients")
    fun postPatientAnagraphicData(@Body patientData: PatientData): Call<Int>

}