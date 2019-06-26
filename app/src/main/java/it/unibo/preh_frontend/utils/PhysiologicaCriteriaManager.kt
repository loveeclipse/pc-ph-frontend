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
    private var context: Context
) {
    fun activeCentralization() {
        val gson = Gson()
        var physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        if (physiologicCriteria == null) {
            physiologicCriteria = PhysiologicCriterionData()
        }
        physiologicCriteria.highRespFreq = true
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
        physiologicCriteria.highRespFreq = false
        val criteriaAsJson = gson.toJson(physiologicCriteria)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        if (CentralizationManager.determineCentralization(context)) {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
        } else {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
        }
    }
}