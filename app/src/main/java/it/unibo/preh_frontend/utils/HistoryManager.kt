package it.unibo.preh_frontend.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import it.unibo.preh_frontend.model.AnagraphicData
import it.unibo.preh_frontend.model.ComplicationsHistoryData
import it.unibo.preh_frontend.model.DrugsData
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.model.ManeuverHistoryData
import it.unibo.preh_frontend.model.NewPcCarData
import it.unibo.preh_frontend.model.NewPcCarReturnData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.TreatmentHistoryData
import it.unibo.preh_frontend.model.VitalParametersData

object HistoryManager {
    private val historyType = object : TypeToken<ArrayList<PreHData>>() {}.type
    private val typeFactory = RuntimeTypeAdapterFactory
            .of(PreHData::class.java, "type")
            .registerSubtype(AnagraphicData::class.java, "AnagraphicData")
            .registerSubtype(ComplicationsHistoryData::class.java, "ComplicationsHistoryData")
            .registerSubtype(ManeuverData::class.java, "ManeuverData")
            .registerSubtype(ManeuverHistoryData::class.java, "ManeuverHistoryData")
            .registerSubtype(PatientStatusData::class.java, "PatientStatusData")
            .registerSubtype(TreatmentData::class.java, "TreatmentData")
            .registerSubtype(TreatmentHistoryData::class.java, "TreatmentHistoryData")
            .registerSubtype(VitalParametersData::class.java, "VitalParametersData")
            .registerSubtype(DrugsData::class.java, "DrugsData")
            .registerSubtype(NewPcCarData::class.java, "NewPcCarData")
            .registerSubtype(NewPcCarReturnData::class.java, "NewPcCarReturnData")

    fun addEntry(data: PreHData, sharedPreferences: SharedPreferences) {
        val localHistoryList = getEntryList(sharedPreferences)
        localHistoryList.add(0, data)
        val historyListAsJson = Gson().toJson(localHistoryList, historyType)
        sharedPreferences.edit().putString("historyList", historyListAsJson).apply()
    }

    fun getEntryList(sharedPreferences: SharedPreferences): ArrayList<PreHData> {
        val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create()
        return gson.fromJson(sharedPreferences.getString("historyList", null), historyType)
    }
}