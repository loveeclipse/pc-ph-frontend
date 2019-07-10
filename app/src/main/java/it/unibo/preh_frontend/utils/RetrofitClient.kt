package it.unibo.preh_frontend.utils

import android.util.Log
import it.unibo.preh_frontend.model.dt_model.Anagraphic
import it.unibo.preh_frontend.model.dt_model.Drug
import it.unibo.preh_frontend.model.dt_model.EventInformation
import it.unibo.preh_frontend.model.dt_model.InjectionTreatment
import it.unibo.preh_frontend.model.dt_model.IppvTreatment
import it.unibo.preh_frontend.model.dt_model.MissionInformation
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
    val discoveryServiceUrl: String = "https://pc-18-preh-discovery.herokuapp.com/"

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
        discoveryService.getService("patients-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                patientServiceUrl = response!!.body().toString()
                patientService = retrofitClient.baseUrl(patientServiceUrl).build().create(PatientPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })
        discoveryService.getService("events-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                eventServiceUrl = response!!.body().toString()
                eventService = retrofitClient.baseUrl(eventServiceUrl).build().create(EventPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })

        discoveryService.getService("missions-service").enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                missionServiceUrl = response!!.body().toString()
                missionService = retrofitClient.baseUrl(missionServiceUrl).build().create(MissionPreHApi::class.java)
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })
    }

    fun createPatient(patientData: PatientData) {
        patientService.postNewPatient(patientData).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                DtIdentifiers.patientId = response!!.body().toString()
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
            }
        })
    }

    fun putAnagraphicData(anagraphic: Anagraphic) {
        if (DtIdentifiers.patientId != null) {
            patientService.putPatientAnagraphic(DtIdentifiers.patientId!!, anagraphic).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun sendTrackingStep(trackingStep: String, trackingStepItem: TrackingStep) {
        if (DtIdentifiers.assignedMission != null) {
            Log.d("TEST","MISSIONID    ${DtIdentifiers.assignedMission}")
            Log.d("TEST","TRACKINGSTEP    $trackingStep")
            missionService.putNewTrackingStep(DtIdentifiers.assignedMission!!, trackingStep, trackingStepItem).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun sendReturnInformation(returnInformation: ReturnInformation) {
        if (DtIdentifiers.assignedMission != null) {
            missionService.insertReturnInformation(DtIdentifiers.assignedMission!!, returnInformation).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun putMissionMedic() {
        if (DtIdentifiers.assignedMission != null) {
            missionService.putMissionMedic(DtIdentifiers.assignedMission!!, DtIdentifiers.doctor!!).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun putMissionOngoingState(ongoingState: Boolean) {
        if (DtIdentifiers.assignedMission != null) {
            missionService.putOngoingStatus(DtIdentifiers.assignedMission!!, ongoingState).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun getOngoingMissions() {
        if (DtIdentifiers.vehicle != null) {
            missionService.getOngoingMissions(DtIdentifiers.vehicle!!).enqueue(object : Callback<OngoingMissions> {
                override fun onFailure(call: Call<OngoingMissions>, t: Throwable) {
                }

                override fun onResponse(call: Call<OngoingMissions>, response: Response<OngoingMissions>) {
                    if (response.code() == 200) {
                        val ongoingMissions = OngoingMissions(response.body()!!.ids, response.body()!!.links)
                        Log.d("TEST","MISIONID   ${ongoingMissions.ids[0]}")
                        DtIdentifiers.assignedMission = ongoingMissions.ids[0]
                        getMissionInformation()
                        putMissionMedic()
                    }
                }
            })
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun getMissionInformation(){
        if(DtIdentifiers.assignedMission != null){
            missionService.getMissionInformation(DtIdentifiers.assignedMission!!).enqueue(object : Callback<MissionInformation>{
                override fun onFailure(call: Call<MissionInformation>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("TEST", call.request().url().toString())
                }

                override fun onResponse(call: Call<MissionInformation>, response: Response<MissionInformation>) {
                   DtIdentifiers.assignedEvent = response.body()!!.eventId
                   Log.d("TEST","EVENTID     ${DtIdentifiers.assignedEvent}")
                }

            })
        }
    }

    // TODO TEST CHE NON CRASHI TUTTO (RUNNA IN THREAD DIVERSO DA QUELLO DA QUELLO DELL'ACTIVITY)
    fun getEventInformation(): EventInformation? {
        return if (DtIdentifiers.assignedEvent != null) {
            eventService.getEventData(DtIdentifiers.assignedEvent!!).execute().body()
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
            null
        }
    }

    fun putPatientStatus(patientStatus: PatientStatus) {
        if (DtIdentifiers.patientId != null) {
            patientService.putPatientStatus(DtIdentifiers.patientId!!, patientStatus).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postPatientVitalParameters(vitalParameters: VitalParameters) {
        if (DtIdentifiers.patientId != null) {
            patientService.postVitalParameters(DtIdentifiers.patientId!!, vitalParameters).enqueue(BasicStringCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postDrugs(drug: Drug) {
        if (DtIdentifiers.patientId != null) {
        patientService.postDrugs(DtIdentifiers.patientId!!, drug).enqueue(BasicStringCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postSimpleManeuver(simpleManeuver: String, executionTime: String) {
        if (DtIdentifiers.patientId != null) {
            patientService.postSimpleManeuver(DtIdentifiers.patientId!!, simpleManeuver, executionTime).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun deleteSimpleManeuver(simpleManeuver: String) {
        if (DtIdentifiers.patientId != null) {
            patientService.deleteSimpleManeuver(DtIdentifiers.patientId!!, simpleManeuver).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postSimpleTreatment(simpleTreatment: SimpleTreatment) {
        if (DtIdentifiers.patientId != null) {
            patientService.postSimpleTreatment(DtIdentifiers.patientId!!, simpleTreatment).enqueue(BasicStringCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postInjectionTreatment(injectionTreatment: InjectionTreatment) {
        if (DtIdentifiers.patientId != null) {
            patientService.postInjectionTreatment(DtIdentifiers.patientId!!, injectionTreatment).enqueue(BasicStringCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postIppvTreatment(ippvTreatment: IppvTreatment) {
        if (DtIdentifiers.patientId != null) {
            patientService.postIppvTreatment(DtIdentifiers.patientId!!, ippvTreatment).enqueue(BasicStringCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun postComplication(complication: String, time: String) {
        if (DtIdentifiers.patientId != null) {
            patientService.postComplication(DtIdentifiers.patientId!!, complication, time).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
    }

    fun deleteComplication(complication: String) {
        if (DtIdentifiers.patientId != null) {
            patientService.deleteComplication(DtIdentifiers.patientId!!, complication).enqueue(BasicVoidCallback)
        } else {
            Log.e("BAD_ID", "The given id was not yet set")
        }
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
            Log.d("TEST", call.request().url().toString())
            Log.d("TEST", response.code().toString())
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            t.printStackTrace()
            Log.d("TEST", call.request().url().toString())
        }
    }
}