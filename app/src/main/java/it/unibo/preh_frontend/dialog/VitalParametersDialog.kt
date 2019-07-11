package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryVitalParametersDialog
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.model.dt_model.VitalParameters
import it.unibo.preh_frontend.utils.DateManager
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.utils.PhysiologicaCriteriaManager
import it.unibo.preh_frontend.utils.RetrofitClient

class VitalParametersDialog : HistoryVitalParametersDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedState: VitalParametersData
    private var parentDialog: Dialog? = null
    private var mLastClickTime = SystemClock.elapsedRealtime()
    private lateinit var saveState: VitalParametersData
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        parentDialog = dialog
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)

        initSpinner()

        setSharedPreferences()

        cardiacFrequencyEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(text: Editable) {
                checkSiSipa(text, arteriousPressureEditText.text)
            }
        })
        arteriousPressureEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(text: Editable) {
                checkSiSipa(cardiacFrequencyEditText.text, text)
            }
        })

        val exitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        exitButton.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                mLastClickTime = SystemClock.elapsedRealtime()
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setCancelable(true)
                    setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                }
                if (checkEveryField()) {
                    builder.apply {
                        setTitle("Conferma Parametri Vitali")
                        setMessage("I dati inseriti saranno salvati")
                        setPositiveButton("Si") { dialog, _ ->
                            dialog.cancel()
                            parentDialog?.cancel()
                        }
                    }
                } else {
                    builder.apply {
                        setTitle("Uscire senza salvare?")
                        setMessage("Inserimento incompleto o errato")
                        setPositiveButton("Si") { dialog, _ ->
                            dialog.cancel()
                            parentDialog?.dismiss()
                        }
                    }
                }
                builder.create().show()
            }
        }

        return root
    }

    private fun checkSiSipa(cardiacFrequencyEditable: Editable, arteriousPressureEditable: Editable) {
        if (cardiacFrequencyEditable.isNotEmpty() &&
                arteriousPressureEditable.isNotEmpty() &&
                cardiacFrequencyEditable.toString().toInt() != 0 &&
                arteriousPressureEditable.toString().toInt() != 0) {
            val siSipaValue = cardiacFrequencyEditable.toString().toDouble() / arteriousPressureEditable.toString().toDouble()
            siSipa.text = "SI/SIPA = $siSipaValue"
            if (siSipaValue > 0.9)
                siSipa.visibility = View.VISIBLE
            else
                siSipa.visibility = View.INVISIBLE
        }
    }

    override fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        respiratoryFreqSpinner.apply {
            adapter = newAdapter
            setSelection(1)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p2 == 2)
                        PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                                context.getString(R.string.frequenza_respiratoria_29_atti_min)).activeCentralization()
                    else
                        PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                            context.getString(R.string.frequenza_respiratoria_29_atti_min)).deactivatesCentralization()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.eyeOpeningItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        eyesOpeningSpinner.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.verbalResponseItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        verbalResponseSpinner.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.motorResponseItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        motorResponseSpinner.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun checkEveryField(): Boolean {
        return (airwaysRadiogroup.checkedRadioButtonId != -1 &&
                saturationEditText.text.toString() != "" &&
                cardiacFrequencyEditText.text.toString() != "" &&
                beatTypeRadiogroup.checkedRadioButtonId != -1 &&
                arteriousPressureEditText.text.toString() != "" &&
                capillarFillingTimeRadioGroup.checkedRadioButtonId != -1 &&
                mucousSkinColourRadiogroup.checkedRadioButtonId != -1 &&
                pupilSxRadiogroup.checkedRadioButtonId != -1 &&
                pupilDxRadiogroup.checkedRadioButtonId != -1 &&
                bodyTempEditText.text.toString() != "")
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("vitalParameters", null), VitalParametersData::class.java)
            if (newSaveState != null) {
                this.activity?.runOnUiThread {
                    airwaysRadiogroup.check(newSaveState.airways)
                    respiratoryFreqSpinner.setSelection(newSaveState.respiratoryFrequency)
                    saturationEditText.setText(newSaveState.periphericalSaturation.toString())
                    cardiacFrequencyEditText.setText(newSaveState.cardiacFrequency.toString())
                    beatTypeRadiogroup.check(newSaveState.beatType)
                    arteriousPressureEditText.setText(newSaveState.bloodPressure.toString())
                    capillarFillingTimeRadioGroup.check(newSaveState.capillarFillTime)
                    mucousSkinColourRadiogroup.check(newSaveState.mucousSkinColor)
                    eyesOpeningSpinner.setSelection(newSaveState.eyesOpening)
                    verbalResponseSpinner.setSelection(newSaveState.verbalResponse)
                    motorResponseSpinner.setSelection(newSaveState.motorResponse)
                    pupilSxRadiogroup.check(newSaveState.pupilSx)
                    pupilDxRadiogroup.check(newSaveState.pupilDx)
                    photoreagentSxSwitch.isChecked = newSaveState.photoreagentSx
                    photoreagentDxSwitch.isChecked = newSaveState.photoreagentDx
                    bodyTempEditText.setText(newSaveState.temperature.toString())
                }
                savedState = newSaveState
            }
        }).start()
    }

    override fun onCancel(dialog: DialogInterface) {
        saveState = VitalParametersData(airwaysRadiogroup.checkedRadioButtonId,
                respiratoryFreqSpinner.selectedItemPosition,
                saturationEditText.text.toString().toInt(),
                cardiacFrequencyEditText.text.toString().toInt(),
                beatTypeRadiogroup.checkedRadioButtonId,
                arteriousPressureEditText.text.toString().toInt(),
                capillarFillingTimeRadioGroup.checkedRadioButtonId,
                mucousSkinColourRadiogroup.checkedRadioButtonId,
                eyesOpeningSpinner.selectedItemPosition,
                verbalResponseSpinner.selectedItemPosition,
                motorResponseSpinner.selectedItemPosition,
                pupilSxRadiogroup.checkedRadioButtonId,
                pupilDxRadiogroup.checkedRadioButtonId,
                photoreagentSxSwitch.isChecked,
                photoreagentDxSwitch.isChecked,
                bodyTempEditText.text.toString().toDouble()
        )
        sendVitalParametersToDt()
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState)
        sharedPreferences.edit().putString("vitalParameters", stateAsJson).apply()
        HistoryManager.addEntry(saveState, sharedPreferences)
        super.onCancel(dialog)
    }

    private fun sendVitalParametersToDt() {
        val airways = when (airwaysRadiogroup.checkedRadioButtonId) {
            R.id.pervious_radio -> "open"
            R.id.impervious_radio -> "closed"
            else -> "open"
        }
        val beatType = when (beatTypeRadiogroup.checkedRadioButtonId) {
            R.id.rithmic_radio -> "rithmic"
            R.id.arithmic_radio -> "arithmic"
            else -> "rithmic"
        }
        val capillarFillingTime = when (capillarFillingTimeRadioGroup.checkedRadioButtonId) {
            R.id.normal_radio -> "normal"
            R.id.increased_radio -> "augmented"
            R.id.null_radio -> "none"
            else -> "normal"
        }
        val skinColor = when (mucousSkinColourRadiogroup.checkedRadioButtonId) {
            R.id.color_normal_radio -> "normal"
            R.id.pale_radio -> "pale"
            R.id.cyanotic_radio -> "cyanotic"
            else -> "normal"
        }
        val leftPupil = when (pupilSxRadiogroup.checkedRadioButtonId) {
            R.id.normalSx_radio -> "normal"
            R.id.midriasisSx_radio -> "mydriasis"
            R.id.miosisSx_radio -> "miosis"
            else -> "normal"
        }
        val rightPupil = when (pupilDxRadiogroup.checkedRadioButtonId) {
            R.id.normalDx_radio -> "normal"
            R.id.midriasisDx_radio -> "mydriasis"
            R.id.miosisDx_radio -> "miosis"
            else -> "normal"
        }
        val eyesOpening = when (eyesOpeningSpinner.selectedItemPosition) {
            0 -> "4 - Spontaneous"
            1 -> "3 - Sound Stimulus"
            2 -> "2 - Pressure Stimulus"
            3 -> "1 - None"
            else -> "ND - Not Determinable"
        }
        val verbalResponse = when (verbalResponseSpinner.selectedItemPosition) {
            0 -> "5 - Oriented"
            1 -> "4 - Confused"
            2 -> "3 - Words"
            3 -> "2 - Sounds"
            4 -> "1 - None"
            else -> "ND - Not Determinable"
        }

        val motorResponse = when (motorResponseSpinner.selectedItemPosition) {
            0 -> "6 - Executes Orders"
            1 -> "5 - Localizes"
            2 -> "4 - Normal Flexion"
            3 -> "3 - Abnormal Flexion"
            4 -> "2 - Extention"
            5 -> "1 - None"
            else -> "ND - Not Determinable"
        }
        val time = DateManager.getStandardRepresentation()
        val vitalParameters = VitalParameters(airways,
                respiratoryFreqSpinner.selectedItem.toString(),
                saveState.periphericalSaturation,
                saveState.cardiacFrequency,
                beatType,
                saveState.bloodPressure,
                capillarFillingTime,
                skinColor,
                eyesOpening,
                verbalResponse,
                motorResponse,
                leftPupil,
                rightPupil,
                saveState.photoreagentSx,
                saveState.photoreagentDx,
                saveState.temperature,
                time)
        RetrofitClient.postPatientVitalParameters(vitalParameters)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }

    override fun calculateGCS(): Int {
        return 4 - eyesOpeningSpinner.selectedItemPosition +
                5 - motorResponseSpinner.selectedItemPosition +
                6 - verbalResponseSpinner.selectedItemPosition
    }
}