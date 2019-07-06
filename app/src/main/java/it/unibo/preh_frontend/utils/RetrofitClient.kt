package it.unibo.preh_frontend.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val okHttpClient = OkHttpClient()

    var retrofitPatients = Retrofit.Builder()
            .baseUrl("127.0.0.1:5150/api/") //No localhost nel futuro
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    //Generare altre istanze di retrofit per ogni servizio

    //Oppure capire se è più utile utilizzare il servizio di discovery

    var service: PreHApi = retrofitPatients.create(PreHApi::class.java)
}