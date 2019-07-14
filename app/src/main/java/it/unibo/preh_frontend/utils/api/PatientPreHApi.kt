package it.unibo.preh_frontend.utils.api

import it.unibo.preh_frontend.model.dt_model.Anagraphic
import it.unibo.preh_frontend.model.dt_model.Drug
import it.unibo.preh_frontend.model.dt_model.InjectionTreatment
import it.unibo.preh_frontend.model.dt_model.IppvTreatment
import it.unibo.preh_frontend.model.dt_model.PatientData
import it.unibo.preh_frontend.model.dt_model.PatientStatus
import it.unibo.preh_frontend.model.dt_model.SimpleTreatment
import it.unibo.preh_frontend.model.dt_model.VitalParameters
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PatientPreHApi {
    @POST("/patients")
    fun postNewPatient(@Body patientData: PatientData): Call<String>

    @PUT("/patients/{patientId}/anagraphic")
    fun putPatientAnagraphic(
        @Path("patientId") patientId: String,
        @Body anagraphic: Anagraphic
    ): Call<Void>

    @PUT("/patients/{patientId}/status")
    fun putPatientStatus(
        @Path("patientId") patientId: String,
        @Body patientStatus: PatientStatus
    ): Call<Void>

    @POST("/patients/{patientId}/vital-parameters")
    fun postVitalParameters(
        @Path("patientId") patientId: String,
        @Body vitalParameters: VitalParameters
    ): Call<String>

    @POST("/patients/{patientId}/drugs")
    fun postDrugs(
        @Path("patientId") patientId: String,
        @Body administeredDrug: Drug
    ): Call<String>

    @POST("/patients/{patientId}/maneuvers/simple/{simpleManeuver}")
    fun postSimpleManeuver(
        @Path("patientId") patientId: String,
        @Path("simpleManeuver") maneuver: String,
        @Body executionTime: String
    ): Call<Void>

    @DELETE("/patients/{patientId}/maneuvers/simple/{simpleManeuver}")
    fun deleteSimpleManeuver(
        @Path("patientId") patientId: String,
        @Path("simpleManeuver") maneuver: String
    ): Call<Void>

    @POST("/patients/{patientId}/treatments/simple")
    fun postSimpleTreatment(
        @Path("patientId") patientId: String,
        @Body simpleTreatment: SimpleTreatment
    ): Call<String>

    @POST("/patients/{patientId}/treatments/injection")
    fun postInjectionTreatment(
        @Path("patientId") patientId: String,
        @Body injectionTreatment: InjectionTreatment
    ): Call<String>

    @POST("/patients/{patientId}/treatments/ippv")
    fun postIppvTreatment(
        @Path("patientId") patientId: String,
        @Body ippvTreatment: IppvTreatment
    ): Call<String>

    @POST("/patients/{patientId}/complications/{complication}")
    fun postComplication(
        @Path("patientId") patientId: String,
        @Path("complication") complicationName: String,
        @Body time: String
    ): Call<Void>

    @DELETE("/patients/{patientId}/complications/{complication}")
    fun deleteComplication(
        @Path("patientId") patientId: String,
        @Path("complication") complicationName: String
    ): Call<Void>
}