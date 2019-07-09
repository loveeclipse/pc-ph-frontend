package it.unibo.preh_frontend.utils.api

import it.unibo.preh_frontend.model.dt_model.OngoingMissions
import it.unibo.preh_frontend.model.dt_model.ReturnInformation
import it.unibo.preh_frontend.model.dt_model.TrackingStep
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionPreHApi {
    @GET("/missions")
    fun getOngoingMissions(
        @Query("vehicle") vehicle: String,
        @Query("ongoing") ongoing: Boolean = true
    ): Call<OngoingMissions>

    @PUT("/missions/{missionId}/return-information")
    fun insertReturnInformation(
        @Path("missionId") missionId: String,
        @Body returnInformation: ReturnInformation
    ): Call<Void>

    @PUT("/missions/{missionId}/tracking/{trackingStep}'")
    fun putNewTrackingStep(
        @Path("missionId") missionId: String,
        @Path("trackingStep") trackingStep: String,
        @Body missionTrackingItem: TrackingStep
    ): Call<Void>
    @PUT("/missions/{missionId}/ongoing")
    fun putOngoingStatus(
        @Path("missionId") missionId: String,
        @Body ongoing: Boolean
    ): Call<Void>

    @PUT("missions/{missionId}/medic")
    fun putMissionMedic(
        @Path("missionId") missionId: String,
        @Body medic: String
    ): Call<Void>
}