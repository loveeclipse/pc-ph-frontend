package it.unibo.preh_frontend.utils.api

import it.unibo.preh_frontend.model.dt_model.TimelineItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface MissionPreHApi {
    @PUT("/events-tracking/{eventId}/missions/{missionId}/chosen-hospital'")
    fun insertChosenHospital(@Path("eventId") eventId: String,
                             @Path("missionId") missionId: String,
                             @Body chosenHospital: String) : Call<Void>

    @PUT("/events-tracking/{eventId}/missions/{missionId}/timeline/oc-call'")
    fun setOcCall(@Path("eventId") eventId: String,
                  @Path("missionId") missionId: String,
                  @Body ocCall: TimelineItem): Call<Void>

    @PUT("/events-tracking/{eventId}/mission/{missionId}/timeline/crew-departure")
    fun setCrewDeparture(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Body crewDeparture: TimelineItem): Call<Void>

    @PUT("/events-tracking/{eventId}/missions/{missionId}/timeline/arrival-onsite")
    fun setArrivalOnSite(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Body siteArrival: TimelineItem): Call<Void>

    @PUT("/events-tracking/{eventId}/missions/{missionId}/timeline/departure-onsite")
    fun setDepartureFromSite(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Body siteDeparture: TimelineItem): Call<Void>

    @PUT("/events-tracking/{eventId}/events/{missionId}/timeline/landing-helipad")
    fun setLandingOnHelipad(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Body landing: TimelineItem): Call<Void>

    @PUT("/events-tracking/{eventId}/missions/{missionId}/timeline/arrival-er")
    fun setArrivalAtER(@Path("eventId") eventId: String,
                         @Path("missionId") missionId: String,
                         @Body arrivalER: TimelineItem): Call<Void>
}