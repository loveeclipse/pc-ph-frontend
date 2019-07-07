package it.unibo.preh_frontend.utils.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DiscoveryApi {
    @GET("discover/{serviceName}")
    fun getService(@Path("serviceName") serviceName: String): Call<String>
}