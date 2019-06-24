package it.unibo.preh_frontend.utils

import android.content.Context
import com.google.gson.Gson
import it.unibo.preh_frontend.model.AnatomicCriterionData
import it.unibo.preh_frontend.model.PhysiologicCriterionData

object CentralizationManager {
    private lateinit var anatomicCriteria: AnatomicCriterionData
    private lateinit var physiologicCriteria: PhysiologicCriterionData
    /* private lateinit var dynamicCriateria: DynamicCriterionData*/
    private val gson = Gson()

    fun determineCentralization(context: Context): Boolean{
        obtainCriteria(context)
        return anatomicCriteria.hasTrueFields() || physiologicCriteria.hasTrueFields()
    }

    private fun obtainCriteria(context: Context){
        val sharedPreferences = context.getSharedPreferences("preHData", Context.MODE_PRIVATE)
        val anatomic = gson.fromJson(sharedPreferences.getString("anatomicCriteria",null),AnatomicCriterionData::class.java)
        if(anatomic == null){
            anatomicCriteria = AnatomicCriterionData()
        }else{
            anatomicCriteria = anatomic
        }
        val physiologic = gson.fromJson(sharedPreferences.getString("physiologicCriteria",null),PhysiologicCriterionData::class.java)
        if(physiologic == null){
            physiologicCriteria = PhysiologicCriterionData()
        }else{
            physiologicCriteria = physiologic
        }
        //Aggiungi criterio dinamico
    }
}