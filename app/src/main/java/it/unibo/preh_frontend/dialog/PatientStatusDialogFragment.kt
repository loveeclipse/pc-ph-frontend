package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        parentDialog = dialog

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)
        determineActiveCriteria()

        closedButton.setOnClickListener {
            setButtonColor(closedButton, resources)
        }
        piercingButton.setOnClickListener {
            setButtonColor(piercingButton, resources)
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
            setButtonColor(positiveEcofastButton, resources)
        }
        negativeEcofastButton.setOnClickListener {
            activateButton(negativeEcofastButton, resources)
            deactivateButton(positiveEcofastButton, resources)
            setButtonColor(negativeEcofastButton, resources)
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
        saveState = PatientStatusData(
                closedTrauma = closedButton.isActivated,
                piercingTrauma = piercingButton.isActivated,
                helmetBelt = helmetBeltSwitch.isChecked,
                hemorrage = hemorrageSwitch.isChecked,
                airways = airwaysSwitch.isChecked,
                tachipnea = tachipneaDyspneaSwitch.isChecked,
                costalVolet = voletSwitch.isChecked,
                ecofastPositive = positiveEcofastButton.isActivated,
                ecofastNegative = negativeEcofastButton.isActivated,
                pelvisStatus = unstablePelvisSwitch.isChecked,
                amputation = amputationSwitch.isChecked,
                sunkenSkull = sunkenSkullButton.isActivated,
                otorrhagia = otorrhagiaButton.isActivated,
                paraparesis = paraparesisButton.isActivated,
                tetraparesis = tetraparesisButton.isActivated,
                paresthesia = paresthesiaButton.isActivated,
                physiologicCriterion = physiologicButton.isActivated,
                anatomicCriterion = anatomicButton.isActivated,
                dynamicCriterion = dynamicButton.isActivated,
                clinicalJudgement = clinicalJudgementButton.isActivated,
                shockIndex = shockIndexText.text.toString().toInt()
                )
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState, PatientStatusData::class.java)
        sharedPreferences.edit().putString("patientState", stateAsJson).apply()
        HistoryManager.addEntry(saveState, sharedPreferences)
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("patientState", null), PatientStatusData::class.java)
            if (newSaveState != null) {
                this.activity?.runOnUiThread {
                    if (newSaveState.closedTrauma) {
                        closedButton.isActivated = true
                        activateButton(closedButton, resources)
                    }
                    if (newSaveState.piercingTrauma) {
                        piercingButton.isActivated = true
                        activateButton(piercingButton, resources)
                    }
                    helmetBeltSwitch.isChecked = newSaveState.helmetBelt
                    hemorrageSwitch.isChecked = newSaveState.hemorrage
                    airwaysSwitch.isChecked = newSaveState.airways
                    tachipneaDyspneaSwitch.isChecked = newSaveState.tachipnea
                    voletSwitch.isChecked = newSaveState.costalVolet
                    if (newSaveState.ecofastPositive) {
                        positiveEcofastButton.isActivated = true
                        activateButton(positiveEcofastButton, resources)
                        deactivateButton(negativeEcofastButton, resources)
                    }
                    if (newSaveState.ecofastNegative) {
                        negativeEcofastButton.isActivated = true
                        activateButton(negativeEcofastButton, resources)
                        deactivateButton(positiveEcofastButton, resources)
                    }
                    unstablePelvisSwitch.isChecked = newSaveState.pelvisStatus
                    amputationSwitch.isChecked = newSaveState.amputation
                    physiologicButton.isActivated = newSaveState.physiologicCriterion
                    if (newSaveState.physiologicCriterion) {
                        physiologicButton.isActivated = true
                        activateButton(physiologicButton, resources)
                    }
                    anatomicButton.isActivated = newSaveState.anatomicCriterion
                    if (newSaveState.anatomicCriterion) {
                        anatomicButton.isActivated = true
                        activateButton(anatomicButton, resources)
                    }
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
            anatomicButton.isActivated = true
            activateButton(anatomicButton, resources)
        } else {
            anatomicButton.isActivated = false
            deactivateButton(anatomicButton, resources)
        }
        if (physiologicCriteria != null && physiologicCriteria.hasTrueFields()) {
            physiologicButton.isActivated = true
            activateButton(physiologicButton, resources)
        } else {
            physiologicButton.isActivated = false
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

    private fun setButtonColor(button: Button, resources: Resources) {
        if (!button.isActivated) {
            button.isActivated = true
            activateButton(button, resources)
        } else {
            button.isActivated = false
            deactivateButton(button, resources)
        }
    }
}
