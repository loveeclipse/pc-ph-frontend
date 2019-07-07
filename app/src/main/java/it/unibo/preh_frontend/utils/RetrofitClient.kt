package it.unibo.preh_frontend.utils

import android.util.Log
import it.unibo.preh_frontend.utils.api.DiscoveryApi
import it.unibo.preh_frontend.utils.api.EventPreHApi
import it.unibo.preh_frontend.utils.api.MissionPreHApi
import it.unibo.preh_frontend.utils.api.PatientPreHApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    lateinit var patientServiceUrl: String
    lateinit var eventServiceUrl: String
    lateinit var missionServiceUrl: String
    val discoveryServiceUrl: String = "localhost:5150/discovery"
    lateinit var patientService: PatientPreHApi
    lateinit var eventService: EventPreHApi
    lateinit var missionService: MissionPreHApi

    val okHttpClient = OkHttpClient()

    var retrofitClient = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)

    //Generare altre istanze di retrofit per ogni servizio

    //Oppure capire se è più utile utilizzare il servizio di discovery

    var discoveryService: DiscoveryApi = retrofitClient.baseUrl(discoveryServiceUrl).build().create(DiscoveryApi::class.java)

    fun obtainServiceLocation(){
        //I vari baseUrl saranno ottenuti dal discoveryService all'avvio applicazione
        discoveryService.getService("patients").enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                patientServiceUrl = response!!.body()
                patientService = retrofitClient.baseUrl(patientServiceUrl).build().create(PatientPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("ERROR","FAILED TO OBTAIN SERVICE LOCATION")
            }
        })
        discoveryService.getService("events").enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?) {

                eventServiceUrl = response!!.body()
                eventService = retrofitClient.baseUrl(eventServiceUrl).build().create(EventPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("ERROR","FAILED TO OBTAIN SERVICE LOCATION")
            }
        })

        discoveryService.getService("missions").enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                missionServiceUrl = response!!.body()
                missionService = retrofitClient.baseUrl(eventServiceUrl).build().create(MissionPreHApi::class.java)

            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("ERROR","FAILED TO OBTAIN SERVICE LOCATION")
            }
        })

        //TODO Controllare che il discovery ritorni il path intero e non solo ip e porta
    }


}