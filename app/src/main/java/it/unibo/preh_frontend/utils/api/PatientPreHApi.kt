package it.unibo.preh_frontend.utils.api


import it.unibo.preh_frontend.model.dt_model.AnagraphicData
import it.unibo.preh_frontend.model.dt_model.Drug
import it.unibo.preh_frontend.model.dt_model.InjectionTreatment
import it.unibo.preh_frontend.model.dt_model.IppvTreatment
import it.unibo.preh_frontend.model.dt_model.PacingManeuver
import it.unibo.preh_frontend.model.dt_model.PatientData
import it.unibo.preh_frontend.model.dt_model.PatientStatus
import it.unibo.preh_frontend.model.dt_model.SimpleTreatment
import it.unibo.preh_frontend.model.dt_model.TimedTreatment
import it.unibo.preh_frontend.model.dt_model.VitalParameters
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PatientPreHApi {
    @POST("patients")
    fun postPatientAnagraphicData(@Body patientData: PatientData): Call<Void>

    @PUT("/patients/{patientId}/anagraphic")
    fun putPatientAnagraphic(@Path("patientId") patientId: String,
                             @Body anagraphicData: AnagraphicData) : Call<Void>

    @GET("/patients/{patientId}/anagraphic")
    fun getPatientAnagraphic(@Path("patientId") patientId: String): Call<AnagraphicData>

    @PUT("/patients/{patientId}/status")
    fun putPatientStatus(@Path("patientId") patientId: String,
                         @Body patientStatus: PatientStatus) : Call<Void>

    @POST("/patients/{patientId}/vital-parameters")
    fun postVitalParameters(@Path("patientId") patientId: String,
                            @Body vitalParameters: VitalParameters): Call<Void>

    @PUT("/patients/{patientId}/vital-parameters/{vitalParametersId}")
    fun putVitalParameters(@Path("patientId") patientId: String,
                           @Path("vitalParametersId") parametersId: String,
                            @Body vitalParameters: VitalParameters): Call<Void>

    @POST("/patients/{patientId}/drugs")
    fun postDrugs(@Path("patientId") patientId: String,
                  @Body administeredDrug: Drug) : Call<Void>

    @POST("/patients/{patientId}/maneuvers/simple/{simpleManeuver}")
    fun postSimpleManeuver(@Path("patientId") patientId: String,
                           @Path("simpleManeuver") maneuver: String,
                           @Body executionTime: String) : Call<Void>

    @POST("/patients/{patientId}/maneuvers/pacing")
    fun postPacingManeuver(@Path("patientId") patientId: String,
                           @Body pacingManeuver: PacingManeuver) : Call<Void>

    /*@GET("/patients/{patientId}/maneuvers/pacing/{pacingManeuverId}")
    fun getPacingManeuverById(@Path("patientId") patientId: String,
                              @Path("simpleManeuver") pacingId: String) : Call<PacingManeuver> */

    @POST("/patients/{patientId}/treatments/simple")
    fun postSimpleTreatment(@Path("patientId") patientId: String,
                            @Body simpleTreatment: SimpleTreatment) : Call<Void>

    @POST("/patients/{patientId}/treatments/timed")
    fun postTimedTreatment(@Path("patientId") patientId: String,
                           @Body timedTreatment: TimedTreatment) : Call<String>

    @PATCH("/patients/{patientId}/treatments/timed/{treatmentId}")
    fun updateTimedTreatment(@Path("patientId") patientId: String,
                             @Path("treatmentId") timedTreatmentId: String,
                             @Body timedTreatment: TimedTreatment) : Call<Void>

    @POST("/patients/{patientId}/treatments/injection")
    fun postInjectionTreatment(@Path("patientId") patientId: String,
                               @Body injectionTreatment: InjectionTreatment) : Call<Void>

    @POST("/patients/{patientId}/treatments/ippv")
    fun postIppvTreatment(@Path("patientId") patientId: String,
                          @Body ippvTreatment: IppvTreatment) : Call<String>

    @POST("/patients/{patientId}/complications/{complication}")
    fun postComplication(@Path("patientId") patientId: String,
                         @Path("complication") complicationName: String,
                         @Body time: String) : Call<Void>

}