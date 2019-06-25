package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryPatientStatusDialog
import it.unibo.preh_frontend.model.AnatomicCriterionData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PhysiologicCriterionData
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.CentralizationManager
import it.unibo.preh_frontend.utils.HistoryManager

class PatientStatusDialogFragment : HistoryPatientStatusDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveState: PatientStatusData
    private lateinit var parentDialog: Dialog

    private var traumaIsClosed: Boolean = false
    private var traumaIsPiercing: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)

        getComponents(root)
        determineActiveCriteria()

        closedButton.setOnClickListener {
            // Case Study
            if (this.traumaIsClosed) {
                deactivateButton(closedButton, resources)
                this.traumaIsClosed = false
            } else {
                activateButton(closedButton, resources)
                this.traumaIsClosed = true
            }
        }

        piercingButton.setOnClickListener {
            if (this.traumaIsPiercing) {
                deactivateButton(piercingButton, resources)
                this.traumaIsPiercing = false
            } else {
                activateButton(piercingButton, resources)
                this.traumaIsPiercing = true
            }
        }

        helmetBeltSwitch.setOnCheckedChangeListener { _, checked ->
            // Case Study
        }

        voletSwitch.setOnCheckedChangeListener { _, checked ->
            val gson = Gson()
            var anatomicCriterionData = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
            if (anatomicCriterionData == null) {
                anatomicCriterionData = AnatomicCriterionData()
            }
            anatomicCriterionData.thoraxDeformity = checked
            val anatomicDataAsJson = gson.toJson(anatomicCriterionData)
            sharedPreferences.edit().putString("anatomicCriteria", anatomicDataAsJson).apply()
            if (CentralizationManager.determineCentralization(requireContext())) {
                requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
            } else {
                requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
            }
            determineActiveCriteria()
        }

        anatomicButton.setOnClickListener {
            AnatomicCriterionDialog().show(requireActivity().supportFragmentManager, "anatomic_criterion_fragment")
        }
        phyisiologicButton.setOnClickListener {
            PhysiologicCriterionDialog().show(requireActivity().supportFragmentManager, "physiologic_criterion_fragment")
        }

        setSharedPreferences()

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.patient_image_button)
        saveAndExitButton.setOnClickListener {
            parentDialog.cancel()
        }
        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        saveState = PatientStatusData(traumaIsClosed,
                traumaIsPiercing,
                helmetBeltSwitch.isChecked, // MISSING PARAMETERS
        voletCostale = voletSwitch.isChecked
                )
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState, PatientStatusData::class.java)
        sharedPreferences.edit().putString("patientState", stateAsJson).apply()

        HistoryManager.addEntry(saveState, sharedPreferences)
        super.onCancel(dialog)
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("patientState", null), PatientStatusData::class.java)
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    if (newSaveState.traumaChiuso) {
                        activateButton(closedButton, resources)
                        traumaIsClosed = true
                    }
                    if (newSaveState.traumaPenetrante) {
                        activateButton(piercingButton, resources)
                        traumaIsPiercing = true
                    }

                    helmetBeltSwitch.isChecked = newSaveState.cascoCintura
                    voletSwitch.isChecked = newSaveState.voletCostale
                }
                saveState = newSaveState
            }
        }).start()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }

    fun determineActiveCriteria() {
        val gson = Gson()
        val anatomicCriteria = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
        val physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        // val dynamicCriteria = gson.fromJson(sharedPreferences.getString("dynamicCriteria",null),DynamicCriterionData::class.java)

        if (anatomicCriteria != null && anatomicCriteria.hasTrueFields()) {
            activateButton(anatomicButton, resources)
        } else {
            deactivateButton(anatomicButton, resources)
        }
        if (physiologicCriteria != null && physiologicCriteria.hasTrueFields()) {
            activateButton(phyisiologicButton, resources)
        } else {
            deactivateButton(phyisiologicButton, resources)
        }
        /*if (dynamicCriteria != null && dynamicCriteria.hasTrueFields()){
            activateButton(dynamicButton, resources)
        } else {
            deactivateButton(dynamicButton, resources)
        }*/
    }
}
