package it.unibo.preh_frontend.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PhysiologicCriterionData

class PhysiologicaCriteriaManager(
    private var sharedPreferences: SharedPreferences,
    private var activity: FragmentActivity,
    private var context: Context,
    private var criteriaName: String
) {
    fun activeCentralization() {
        val gson = Gson()
        var physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        if (physiologicCriteria == null) {
            physiologicCriteria = PhysiologicCriterionData()
        }
        compareAndActiveCriteria(criteriaName, physiologicCriteria)
        val criteriaAsJson = gson.toJson(physiologicCriteria)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        if (CentralizationManager.determineCentralization(context)) {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
        } else {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
        }
    }

    fun deactivatesCentralization() {
        val gson = Gson()
        var physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        if (physiologicCriteria == null) {
            physiologicCriteria = PhysiologicCriterionData()
        }
        compareAndDeactivateCriteria(criteriaName, physiologicCriteria)
        val criteriaAsJson = gson.toJson(physiologicCriteria)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        if (CentralizationManager.determineCentralization(context)) {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
        } else {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
        }
    }

    private fun compareAndActiveCriteria(string: String, physiologicCriteria: PhysiologicCriterionData) {
        when (string) {
            context.getString(R.string.gcs_14) -> {
                physiologicCriteria.gcsValue = true
            }
            context.getString(R.string.frequenza_respiratoria_10_atti_min) -> {
                physiologicCriteria.lowRespFreq = true
            }
            context.getString(R.string.frequenza_respiratoria_29_atti_min) -> {
                physiologicCriteria.highRespFreq = true
            }
            context.getString(R.string.necessit_supporto_ventilatorio) -> {
                physiologicCriteria.ventSupport = true
            }
            context.getString(R.string.pressione_arteriosa_sistolica_90_mmhg) -> {
                physiologicCriteria.lowBloodPress = true
            }
            context.getString(R.string.ipertensione_arteriosa_sintomatica) -> {
                physiologicCriteria.hypertension = true
            }
        }
    }
    private fun compareAndDeactivateCriteria(string: String, physiologicCriteria: PhysiologicCriterionData) {
        when (string) {
            context.getString(R.string.gcs_14) -> {
                physiologicCriteria.gcsValue = false
            }
            context.getString(R.string.frequenza_respiratoria_10_atti_min) -> {
                physiologicCriteria.lowRespFreq = false
            }
            context.getString(R.string.frequenza_respiratoria_29_atti_min) -> {
                physiologicCriteria.highRespFreq = false
            }
            context.getString(R.string.necessit_supporto_ventilatorio) -> {
                physiologicCriteria.ventSupport = false
            }
            context.getString(R.string.pressione_arteriosa_sistolica_90_mmhg) -> {
                physiologicCriteria.lowBloodPress = false
            }
            context.getString(R.string.ipertensione_arteriosa_sintomatica) -> {
                physiologicCriteria.hypertension = false
            }
        }
    }
}