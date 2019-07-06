package it.unibo.preh_frontend.utils


import it.unibo.preh_frontend.dt_model.DtPatientData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.PUT

interface PreHApi {
    @POST("patients")
    fun postPatientAnagraphicData(@Body patientData: DtPatientData): Call<Int>

}