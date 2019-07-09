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

    var patientServiceUrl: String = "http://192.168.1.113:10003/"
    lateinit var eventServiceUrl: String
    lateinit var missionServiceUrl: String
    val discoveryServiceUrl: String = "http://192.168.1.113:5150/"

    lateinit var eventService: EventPreHApi
    lateinit var missionService: MissionPreHApi
    lateinit var patientService: PatientPreHApi

    val okHttpClient = OkHttpClient()

    var retrofitClient = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)

    var discoveryService: DiscoveryApi = retrofitClient.baseUrl(discoveryServiceUrl).build().create(DiscoveryApi::class.java)

    fun obtainServiceLocation() {
        // I vari baseUrl saranno ottenuti dal discoveryService all'avvio applicazione
        discoveryService.getService("patients-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TEST", response!!.body())
                Log.d("TEST", response.message())
                patientServiceUrl = response!!.body()
                patientService = retrofitClient.baseUrl(patientServiceUrl).build().create(PatientPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.d("TEST", call!!.request().url().toString())
                t!!.printStackTrace()
                Log.e("ERROR", "FAILED TO OBTAIN SERVICE LOCATION")
            }
        })
        discoveryService.getService("events-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TEST", response!!.body())
                eventServiceUrl = response!!.body()
                eventService = retrofitClient.baseUrl(eventServiceUrl).build().create(EventPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
                Log.d("TEST", call!!.request().url().toString())
                Log.e("ERROR", "FAILED TO OBTAIN SERVICE LOCATION")
            }
        })

        discoveryService.getService("missions-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TEST", response!!.body())
                missionServiceUrl = response!!.body()
                missionService = retrofitClient.baseUrl(eventServiceUrl).build().create(MissionPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
                Log.d("TEST", call!!.request().url().toString())
                Log.e("ERROR", "FAILED TO OBTAIN SERVICE LOCATION")
            }
        })

        // TODO Controllare che il discovery ritorni il path intero e non solo ip e porta
    }
}