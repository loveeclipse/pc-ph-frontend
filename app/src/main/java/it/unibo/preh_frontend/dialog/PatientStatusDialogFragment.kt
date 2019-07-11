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
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.model.dt_model.PatientStatus
import it.unibo.preh_frontend.utils.AnatomicCriteriaManager
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.CentralizationManager
import it.unibo.preh_frontend.utils.DateManager
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.utils.RetrofitClient

class PatientStatusDialogFragment : HistoryPatientStatusDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveState: PatientStatusData
    private var ecofastState = false
    private var parentDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        parentDialog = dialog

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)
        determineActiveCriteria()
        setSharedPreferences()

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
            ecofastState = true
        }
        negativeEcofastButton.setOnClickListener {
            activateButton(negativeEcofastButton, resources)
            deactivateButton(positiveEcofastButton, resources)
            setButtonColor(negativeEcofastButton, resources)
            ecofastState = false
        }

        anatomicButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("anatomic_criterion_fragment") == null)
                AnatomicCriterionDialog().show(requireActivity().supportFragmentManager, "anatomic_criterion_fragment")
        }
        physiologicButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("physiologic_criterion_fragment") == null)
                PhysiologicCriterionDialog().show(requireActivity().supportFragmentManager, "physiologic_criterion_fragment")
        }

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
                hemorrhage = hemorrageSwitch.isChecked,
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
                paraesthesia = paresthesiaButton.isActivated,
                physiologicCriterion = physiologicButton.isActivated,
                anatomicCriterion = anatomicButton.isActivated,
                dynamicCriterion = dynamicButton.isActivated,
                clinicalJudgement = clinicalJudgementButton.isActivated,
                shockIndex = shockIndexText.text.toString().toDouble()
        )
        sendStatusToDt()
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState, PatientStatusData::class.java)
        sharedPreferences.edit().putString("patientState", stateAsJson).apply()
        HistoryManager.addEntry(saveState, sharedPreferences)
    }

    private fun sendStatusToDt() {
        val time = DateManager.getStandardRepresentation()
        val patientState = PatientStatus(CentralizationManager.centralizationIsActive,
                saveState.closedTrauma,
                saveState.piercingTrauma,
                saveState.helmetBelt,
                saveState.hemorrhage,
                saveState.airways,
                saveState.tachipnea,
                saveState.costalVolet,
                ecofastState,
                saveState.pelvisStatus,
                saveState.amputation,
                saveState.sunkenSkull,
                saveState.otorrhagia,
                saveState.paraparesis,
                saveState.tetraparesis,
                saveState.paraesthesia,
                time)
        RetrofitClient.putPatientStatus(patientState)
    }

    private fun setSharedPreferences() {
        val vitalParametersData = Gson().fromJson(sharedPreferences.getString("vitalParameters", null), VitalParametersData::class.java)
        if (vitalParametersData != null && vitalParametersData.bloodPressure != 0 && vitalParametersData.cardiacFrequency != 0) {
            val siSipaValue = (vitalParametersData.cardiacFrequency / vitalParametersData.bloodPressure).toDouble()
            shockIndexText.text = siSipaValue.toString()
        }
        Thread(Runnable {
            val gson = Gson()
            val patientStatusData = gson.fromJson(sharedPreferences.getString("patientState", null), PatientStatusData::class.java)
            if (patientStatusData != null) {
                this.activity?.runOnUiThread {
                    if (patientStatusData.closedTrauma) {
                        closedButton.isActivated = true
                        activateButton(closedButton, resources)
                    }
                    if (patientStatusData.piercingTrauma) {
                        piercingButton.isActivated = true
                        activateButton(piercingButton, resources)
                    }
                    helmetBeltSwitch.isChecked = patientStatusData.helmetBelt
                    hemorrageSwitch.isChecked = patientStatusData.hemorrhage
                    airwaysSwitch.isChecked = patientStatusData.airways
                    tachipneaDyspneaSwitch.isChecked = patientStatusData.tachipnea
                    voletSwitch.isChecked = patientStatusData.costalVolet
                    if (patientStatusData.ecofastPositive) {
                        positiveEcofastButton.isActivated = true
                        activateButton(positiveEcofastButton, resources)
                        deactivateButton(negativeEcofastButton, resources)
                    }
                    if (patientStatusData.ecofastNegative) {
                        negativeEcofastButton.isActivated = true
                        activateButton(negativeEcofastButton, resources)
                        deactivateButton(positiveEcofastButton, resources)
                    }
                    unstablePelvisSwitch.isChecked = patientStatusData.pelvisStatus
                    amputationSwitch.isChecked = patientStatusData.amputation
                    determineActiveCriteria()
                }
                saveState = patientStatusData
                println("--------------------- savedState in set: ${saveState.physiologicCriterion} ")
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
