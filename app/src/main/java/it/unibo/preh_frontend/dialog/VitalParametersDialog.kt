package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
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
import it.unibo.preh_frontend.utils.HistoryManager

class VitalParametersDialog : HistoryVitalParametersDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedState: VitalParametersData
    private var parentDialog: Dialog? = null

    private lateinit var saveState: VitalParametersData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        parentDialog = dialog
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)

        initSpinner()

        setSharedPreferences()

        val exitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        exitButton.setOnClickListener {
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
                    setMessage("Inserimento incompleto")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog?.dismiss()
                    }
                }
            }
            val exitDialog = builder.create()
            exitDialog.show()
        }

        return root
    }

    override fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        respiratoryFreqSpinner.apply {
            adapter = newAdapter
            setSelection(1)
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
                pupilDXRadiogroup.checkedRadioButtonId != -1 &&
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
                    pupilDXRadiogroup.check(newSaveState.pupilDx)
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
                pupilDXRadiogroup.checkedRadioButtonId,
                photoreagentSxSwitch.isChecked,
                photoreagentDxSwitch.isChecked,
                bodyTempEditText.text.toString().toDouble()
        )
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState)
        sharedPreferences.edit().putString("vitalParameters", stateAsJson).apply()
        HistoryManager.addEntry(saveState, sharedPreferences)
        super.onCancel(dialog)
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