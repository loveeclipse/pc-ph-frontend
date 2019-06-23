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
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryPatientStatusDialog
import it.unibo.preh_frontend.model.AnagraphicData
import it.unibo.preh_frontend.model.ComplicationsData
import it.unibo.preh_frontend.model.DrugsData
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.RuntimeTypeAdapterFactory

class PatientStatusDialogFragment : HistoryPatientStatusDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveState: PatientStatusData
    private lateinit var localHistoryList: ArrayList<PreHData>
    private lateinit var  parentDialog: Dialog

    private var traumaIsClosed: Boolean = false
    private var traumaIsPiercing: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)

        val historyType = object : TypeToken<ArrayList<PreHData>>() {}.type

        val typeFactory = RuntimeTypeAdapterFactory
                .of(PreHData::class.java, "type")
                .registerSubtype(AnagraphicData::class.java, "AnagraphicData")
                .registerSubtype(ComplicationsData::class.java, "ComplicationsData")
                .registerSubtype(ManeuverData::class.java, "ManeuverData")
                .registerSubtype(PatientStatusData::class.java, "PatientStatusData")
                .registerSubtype(TreatmentData::class.java, "TreatmentData")
                .registerSubtype(VitalParametersData::class.java, "VitalParametersData")
                .registerSubtype(DrugsData::class.java, "DrugsData")

        val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create()

        localHistoryList = gson.fromJson(sharedPreferences.getString("historyList", null), historyType)

        getComponents(root)

        chiusoButton.setOnClickListener {
            if (this.traumaIsClosed) {
                deactivateButton(chiusoButton, resources)
                this.traumaIsClosed = false
            } else {
                activateButton(chiusoButton, resources)
                this.traumaIsClosed = true
            }
        }

        penetranteButton.setOnClickListener {
            if (this.traumaIsPiercing) {
                deactivateButton(penetranteButton, resources)
                this.traumaIsPiercing = false
            } else {
                activateButton(penetranteButton, resources)
                this.traumaIsPiercing = true
            }
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
                cascoCinturaSwitch.isChecked, // MISSING PARAMETERS
                time = "13:00     17/06/2019"
                )
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState, PatientStatusData::class.java)
        sharedPreferences.edit().putString("patientState", stateAsJson).apply()

        localHistoryList.add(saveState)
        val historyType = object : TypeToken<ArrayList<PreHData>>() {
        }.type

        val historyListAsJson = gson.toJson(localHistoryList, historyType)
        sharedPreferences.edit().putString("historyList", historyListAsJson).apply()
        super.onCancel(dialog)
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("patientState", null), PatientStatusData::class.java)
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    if (newSaveState.traumaChiuso) {
                        activateButton(chiusoButton, resources)
                        traumaIsClosed = true
                    }
                    if (newSaveState.traumaPenetrante) {
                        activateButton(penetranteButton, resources)
                        traumaIsPiercing = true
                    }

                    cascoCinturaSwitch.isChecked = newSaveState.cascoCintura
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
}
