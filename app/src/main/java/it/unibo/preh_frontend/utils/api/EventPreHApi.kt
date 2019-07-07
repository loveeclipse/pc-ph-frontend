package it.unibo.preh_frontend.utils.api

import it.unibo.preh_frontend.dt_model.EventInformation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventPreHApi {
    @GET("events/{eventId}")
    fun getEventData(@Path("eventId") eventId : String) : Call<EventInformation>
}