package it.unibo.preh_frontend.utils.api

import it.unibo.preh_frontend.dt_model.TimelineItem
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.PUT
import retrofit2.http.Path

interface MissionPreHApi {
    @PUT("/events-tracking/{eventId}/missions/{missionId}/chosen-hospital'")
    fun insertChosenHospital(@Path("eventId") eventId: String,
                             @Path("missionId") missionId: String,
                             @Field("chosenHospital") chosenHospital: String) : Call<Int>

    @PUT("/events-tracking/{eventId}/missions/{missionId}/timeline/oc-call'")
    fun setOcCall(@Path("eventId") eventId: String,
                  @Path("missionId") missionId: String,
                  @Field("ocCall") ocCall: TimelineItem): Call<Int>

    @PUT("/events-tracking/{eventId}/mission/{missionId}/timeline/crew-departure")
    fun setCrewDeparture(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Field("crewDeparture") crewDeparture: TimelineItem): Call<Int>
}