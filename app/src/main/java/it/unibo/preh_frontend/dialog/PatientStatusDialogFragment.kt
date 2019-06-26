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
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryPatientStatusDialog
import it.unibo.preh_frontend.model.AnatomicCriterionData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PhysiologicCriterionData
import it.unibo.preh_frontend.utils.AnatomicCriteriaManager
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.HistoryManager

class PatientStatusDialogFragment : HistoryPatientStatusDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveState: PatientStatusData
    private var parentDialog: Dialog? = null

    private var traumaIsClosed: Boolean = false
    private var traumaIsPiercing: Boolean = false

    private var ecofastIsPositive: Boolean = false

    private var physiologicIsActive: Boolean = false
    private var anatomicIsActive: Boolean = false
    private var dynamicIsActive: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        parentDialog = dialog

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

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

        voletSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                AnatomicCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                        this.getString(R.string.volet_costale_id)).activeCentralization()
            else
                AnatomicCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                        this.getString(R.string.volet_costale_id)).deactivatesCentralization()
            determineActiveCriteria()
        }

        positiveEcofastButton.setOnClickListener {
            activateButton(positiveEcofastButton, resources)
            deactivateButton(negativeEcofastButton, resources)
            ecofastIsPositive = true
        }
        negativeEcofastButton.setOnClickListener {
            activateButton(negativeEcofastButton, resources)
            deactivateButton(positiveEcofastButton, resources)
            ecofastIsPositive = false
        }

        anatomicButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("anatomic_criterion_fragment") == null)
                AnatomicCriterionDialog().show(requireActivity().supportFragmentManager, "anatomic_criterion_fragment")
        }
        physiologicButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("physiologic_criterion_fragment") == null)
                PhysiologicCriterionDialog().show(requireActivity().supportFragmentManager, "physiologic_criterion_fragment")
        }

        setSharedPreferences()

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.patient_image_button)
        saveAndExitButton.setOnClickListener {
            parentDialog?.cancel()
        }
        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        saveState = PatientStatusData(traumaIsClosed,
                traumaIsPiercing,
                helmetBeltSwitch.isChecked, // MISSING PARAMETERS
                ecofast = ecofastIsPositive,
                costalVolet = voletSwitch.isChecked,
                physiologicCriterion = physiologicIsActive,
                anatomicCriterion = anatomicIsActive
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
                this.activity?.runOnUiThread {
                    if (newSaveState.closedTrauma) {
                        activateButton(closedButton, resources)
                        traumaIsClosed = true
                    }
                    if (newSaveState.piercingTrauma) {
                        activateButton(piercingButton, resources)
                        traumaIsPiercing = true
                    }
                    if (newSaveState.ecofast) {
                        activateButton(positiveEcofastButton, resources)
                        deactivateButton(negativeEcofastButton, resources)
                        ecofastIsPositive = true
                    } else {
                        activateButton(negativeEcofastButton, resources)
                        deactivateButton(positiveEcofastButton, resources)
                        ecofastIsPositive = false
                    }

                    helmetBeltSwitch.isChecked = newSaveState.helmetBelt
                    voletSwitch.isChecked = newSaveState.costalVolet
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

    private fun determineActiveCriteria() {
        val gson = Gson()
        val anatomicCriteria = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
        val physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        // val dynamicCriteria = gson.fromJson(sharedPreferences.getString("dynamicCriteria",null),DynamicCriterionData::class.java)

        if (anatomicCriteria != null && anatomicCriteria.hasTrueFields()) {
            anatomicIsActive = true
            activateButton(anatomicButton, resources)
        } else {
            anatomicIsActive = false
            deactivateButton(anatomicButton, resources)
        }
        if (physiologicCriteria != null && physiologicCriteria.hasTrueFields()) {
            physiologicIsActive = true
            activateButton(physiologicButton, resources)
        } else {
            physiologicIsActive = false
            deactivateButton(physiologicButton, resources)
        }
        /*if (dynamicCriteria != null && dynamicCriteria.hasTrueFields()){
            dynamicIsActive = true
            activateButton(dynamicButton, resources)
        } else {
            dynamicIsActive = false
            deactivateButton(dynamicButton, resources)
        }*/
    }
}
