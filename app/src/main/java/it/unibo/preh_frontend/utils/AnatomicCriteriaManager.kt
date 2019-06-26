package it.unibo.preh_frontend.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.AnatomicCriterionData

class AnatomicCriteriaManager(
    private var sharedPreferences: SharedPreferences,
    private var activity: FragmentActivity,
    private var context: Context,
    private var criteriaName: String
) {
    fun activeCentralization() {
        val gson = Gson()
        var anatomicCriteria = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
        if (anatomicCriteria == null) {
            anatomicCriteria = AnatomicCriterionData()
        }
        compareAndActiveCriteria(criteriaName, anatomicCriteria)
        val criteriaAsJson = gson.toJson(anatomicCriteria)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        if (CentralizationManager.determineCentralization(context)) {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
        } else {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
        }
    }

    fun deactivatesCentralization() {
        val gson = Gson()
        var anatomicCriteria = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
        if (anatomicCriteria == null) {
            anatomicCriteria = AnatomicCriterionData()
        }
        compareAndDeactivateCriteria(criteriaName, anatomicCriteria)
        val criteriaAsJson = gson.toJson(anatomicCriteria)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        if (CentralizationManager.determineCentralization(context)) {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
        } else {
            activity.findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
        }
    }

    private fun compareAndActiveCriteria(string: String, anatomicCriteria: AnatomicCriterionData) {
        when (string) {
            context.getString(R.string.trauma_schiacciamento_tronco) -> {
                anatomicCriteria.traumaTorsoCrush = true
            }
            context.getString(R.string.ferita_penetrante) -> {
                anatomicCriteria.penetratingWound = true
            }
            context.getString(R.string.frattura_cranica_io) -> {
                anatomicCriteria.craniumFracture = true
            }
            context.getString(R.string.volet_costale_id) -> {
                anatomicCriteria.thoraxDeformity = true
            }
            context.getString(R.string.ustione) -> {
                anatomicCriteria.bodyBurn = true
            }
            context.getString(R.string.bacino_instabile_deformato) -> {
                anatomicCriteria.unstablePelvis = true
            }
            context.getString(R.string.sospetta_lesione_verbale) -> {
                anatomicCriteria.vertebralLesion = true
            }
            context.getString(R.string.amputazione_scuoiamento_arto) -> {
                anatomicCriteria.amputation = true
            }
        }
    }
    private fun compareAndDeactivateCriteria(string: String, anatomicCriteria: AnatomicCriterionData) {
        when (string) {
            context.getString(R.string.trauma_schiacciamento_tronco) -> {
                anatomicCriteria.traumaTorsoCrush = false
            }
            context.getString(R.string.ferita_penetrante) -> {
                anatomicCriteria.penetratingWound = false
            }
            context.getString(R.string.frattura_cranica_io) -> {
                anatomicCriteria.craniumFracture = false
            }
            context.getString(R.string.volet_costale_id) -> {
                anatomicCriteria.thoraxDeformity = false
            }
            context.getString(R.string.ustione) -> {
                anatomicCriteria.bodyBurn = false
            }
            context.getString(R.string.bacino_instabile_deformato) -> {
                anatomicCriteria.unstablePelvis = false
            }
            context.getString(R.string.sospetta_lesione_verbale) -> {
                anatomicCriteria.vertebralLesion = false
            }
            context.getString(R.string.amputazione_scuoiamento_arto) -> {
                anatomicCriteria.amputation = false
            }
        }
    }
}