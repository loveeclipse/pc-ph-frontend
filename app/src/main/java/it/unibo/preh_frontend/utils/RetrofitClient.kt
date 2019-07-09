package it.unibo.preh_frontend.utils

import android.util.Log
import it.unibo.preh_frontend.model.dt_model.Anagraphic
import it.unibo.preh_frontend.model.dt_model.Drug
import it.unibo.preh_frontend.model.dt_model.EventInformation
import it.unibo.preh_frontend.model.dt_model.InjectionTreatment
import it.unibo.preh_frontend.model.dt_model.IppvTreatment
import it.unibo.preh_frontend.model.dt_model.OngoingMissions
import it.unibo.preh_frontend.model.dt_model.PatientData
import it.unibo.preh_frontend.model.dt_model.PatientStatus
import it.unibo.preh_frontend.model.dt_model.ReturnInformation
import it.unibo.preh_frontend.model.dt_model.SimpleTreatment
import it.unibo.preh_frontend.model.dt_model.TrackingStep
import it.unibo.preh_frontend.model.dt_model.VitalParameters
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
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    lateinit var patientServiceUrl: String
    lateinit var eventServiceUrl: String
    lateinit var missionServiceUrl: String
    val discoveryServiceUrl: String = "http://192.168.1.113:5150/"

    lateinit var eventService: EventPreHApi
    lateinit var missionService: MissionPreHApi
    lateinit var patientService: PatientPreHApi

    val okHttpClient = OkHttpClient()

    var retrofitClient = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)

    var discoveryService: DiscoveryApi = retrofitClient.baseUrl(discoveryServiceUrl).build().create(DiscoveryApi::class.java)

    fun obtainServiceLocation() {
        // I vari baseUrl saranno ottenuti dal discoveryService all'avvio applicazione
        discoveryService.getService("patients-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TEST", call!!.request().url().toString())
                Log.d("TEST", response!!.body())
                Log.d("TEST", response!!.code().toString())
                Log.d("TEST", response.message())
                patientServiceUrl = response!!.body().toString()
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
                Log.d("TEST", call!!.request().url().toString())
                Log.d("TEST", response!!.body())
                Log.d("TEST", response!!.code().toString())
                eventServiceUrl = response!!.body().toString()
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
                Log.d("TEST", call!!.request().url().toString())
                Log.d("TEST", response!!.code().toString())
                Log.d("TEST", response!!.body())
                missionServiceUrl = response!!.body().toString()
                missionService = retrofitClient.baseUrl(missionServiceUrl).build().create(MissionPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
                Log.d("TEST", call!!.request().url().toString())
                Log.e("ERROR", "FAILED TO OBTAIN SERVICE LOCATION")
            }
        })
    }

    fun createPatient(patientData: PatientData) {
        patientService.postNewPatient(patientData).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TEST", call!!.request().url().toString())
                Log.d("TEST", response!!.code().toString())
                DtIdentifiers.patientId = response.body().toString()
                Log.d("TEST", DtIdentifiers.patientId)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
                Log.d("TEST", call!!.request().url().toString())
            }
        })
    }

    // TODO AGGIUNGERE PUT PER L'ANAGRAFICA

    fun putAnagraphicData(anagraphic: Anagraphic) {
        patientService.putPatientAnagraphic(DtIdentifiers.patientId, anagraphic).enqueue(BasicVoidCallback)
    }

    fun sendTrackingStep(trackingStep: String, trackingStepItem: TrackingStep) {
        missionService.putNewTrackingStep(DtIdentifiers.assignedMission, trackingStep, trackingStepItem).enqueue(BasicVoidCallback)
    }

    fun sendReturnInformation(returnInformation: ReturnInformation) {
        missionService.insertReturnInformation(DtIdentifiers.assignedMission, returnInformation).enqueue(BasicVoidCallback)
    }

    fun putMissionMedic() {
        missionService.putMissionMedic(DtIdentifiers.assignedMission, DtIdentifiers.doctor).enqueue(BasicVoidCallback)
    }

    fun putMissionOngoingState(ongoingState: Boolean) {
        missionService.putOngoingStatus(DtIdentifiers.assignedMission, ongoingState).enqueue(BasicVoidCallback)
    }

    fun getOngoingMissions() {
        missionService.getOngoingMissions(DtIdentifiers.vehicle).enqueue(object : Callback<OngoingMissions> {
            override fun onFailure(call: Call<OngoingMissions>, t: Throwable) {
                TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<OngoingMissions>, response: Response<OngoingMissions>) {
                TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    // TODO TEST CHE NON CRASHI TUTTO (RUNNA IN THREAD DIVERSO DA QUELLO DA QUELLO DELL'ACTIVITY)
    fun getEventInformation(): EventInformation? {
        return eventService.getEventData(DtIdentifiers.assignedEvent).execute().body()
    }

    fun putPatientStatus(patientStatus: PatientStatus) {
        patientService.putPatientStatus(DtIdentifiers.patientId, patientStatus).enqueue(BasicVoidCallback)
    }

    fun postPatientVitalParameters(vitalParameters: VitalParameters) {
        patientService.postVitalParameters(DtIdentifiers.patientId, vitalParameters).enqueue(BasicStringCallback)
    }

    fun postDrugs(drug: Drug) {
        patientService.postDrugs(DtIdentifiers.patientId, drug).enqueue(BasicStringCallback)
    }

    fun postSimpleManeuver(simpleManeuver: String, executionTime: String) {
        patientService.postSimpleManeuver(DtIdentifiers.patientId, simpleManeuver, executionTime).enqueue(BasicVoidCallback)
    }

    fun deleteSimpleManeuver(simpleManeuver: String) {
        patientService.deleteSimpleManeuver(DtIdentifiers.patientId, simpleManeuver).enqueue(BasicVoidCallback)
    }

    fun postSimpleTreatment(simpleTreatment: SimpleTreatment) {
        patientService.postSimpleTreatment(DtIdentifiers.patientId, simpleTreatment).enqueue(BasicStringCallback)
    }

    fun postInjectionTreatment(injectionTreatment: InjectionTreatment) {
        patientService.postInjectionTreatment(DtIdentifiers.patientId, injectionTreatment).enqueue(BasicStringCallback)
    }

    fun postIppvTreatment(ippvTreatment: IppvTreatment) {
        patientService.postIppvTreatment(DtIdentifiers.patientId, ippvTreatment).enqueue(BasicStringCallback)
    }

    fun postComplication(complication: String, time: String) {
        patientService.postComplication(DtIdentifiers.patientId, complication, time).enqueue(BasicVoidCallback)
    }

    fun deleteComplication(complication: String) {
        patientService.deleteComplication(DtIdentifiers.patientId, complication).enqueue(BasicVoidCallback)
    }

    object BasicVoidCallback : Callback<Void> {
        override fun onFailure(call: Call<Void>, t: Throwable) {
            t.printStackTrace()
            Log.d("TEST", call.request().url().toString())
        }

        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            Log.d("TEST", call.request().url().toString())
            Log.d("TEST", response.code().toString())
        }
    }

    object BasicStringCallback : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
        }
    }
}