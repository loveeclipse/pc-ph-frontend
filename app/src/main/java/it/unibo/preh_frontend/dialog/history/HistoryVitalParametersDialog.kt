package it.unibo.preh_frontend.dialog.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.VitalParametersData

open class HistoryVitalParametersDialog : DialogFragment() {
    protected lateinit var airwaysRadiogroup: RadioGroup
    protected lateinit var respiratoryFreqSpinner: Spinner
    protected lateinit var saturationEditText: EditText
    protected lateinit var cardiacFrequencyEditText: EditText
    protected lateinit var beatTypeRadiogroup: RadioGroup
    protected lateinit var arteriousPressureEditText: EditText
    protected lateinit var capillarFillingTimeRadioGroup: RadioGroup
    protected lateinit var mucousSkinColourRadiogroup: RadioGroup
    protected lateinit var eyesOpeningSpinner: Spinner
    protected lateinit var verbalResponseSpinner: Spinner
    protected lateinit var motorResponseSpinner: Spinner
    protected lateinit var pupilSxRadiogroup: RadioGroup
    protected lateinit var pupilDXRadiogroup: RadioGroup
    protected lateinit var photoreagentSxSwitch: Switch
    protected lateinit var photoreagentDxSwitch: Switch
    protected lateinit var bodyTempEditText: EditText
    protected lateinit var gcsTextView: TextView
    protected lateinit var siSipa: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)

        getComponents(root)

        initSpinner()

        setData(arguments?.get("data") as VitalParametersData)

        val exitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        exitButton.setOnClickListener {
                // Here you can save eventual modifications to the history element
                dialog?.cancel()
            }

        return root
    }

    protected open fun getComponents(root: View) {
        root.apply {
            airwaysRadiogroup = findViewById(R.id.airways_radiogroup)
            respiratoryFreqSpinner = findViewById(R.id.resp_freq_spinner)
            saturationEditText = findViewById(R.id.saturation_edittext)
            cardiacFrequencyEditText = findViewById(R.id.cardiac_frequency_edittext)
            beatTypeRadiogroup = findViewById(R.id.beat_type_radiogroup)
            arteriousPressureEditText = findViewById(R.id.pres_arter_edittext)
            capillarFillingTimeRadioGroup = findViewById(R.id.riempimento_capillare_radiogroup)
            mucousSkinColourRadiogroup = findViewById(R.id.mucous_skin_color_radiogroup)
            eyesOpeningSpinner = findViewById(R.id.eyes_opening_spinner)
            verbalResponseSpinner = findViewById(R.id.verbal_response_spinner)
            motorResponseSpinner = findViewById(R.id.motor_response_spinner)
            pupilSxRadiogroup = findViewById(R.id.pupilSx_radiogroup)
            pupilDXRadiogroup = findViewById(R.id.pupilDx_radiogroup)
            photoreagentSxSwitch = findViewById(R.id.photoreagentSx_switch)
            photoreagentDxSwitch = findViewById(R.id.photoreagentDx_switch)
            bodyTempEditText = findViewById(R.id.body_temperature_edittext)
            gcsTextView = findViewById(R.id.gcs_textview)
            siSipa = findViewById(R.id.si_sipa)
        }
    }

    protected open fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        respiratoryFreqSpinner.apply {
            adapter = newAdapter
            setSelection(1)
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.eyeOpeningItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
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
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
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
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
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

    protected open fun setData(data: VitalParametersData) {
        airwaysRadiogroup.check(data.airways)
        respiratoryFreqSpinner.setSelection(data.respiratoryFrequency)
        saturationEditText.setText(data.periphericalSaturation.toString())
        cardiacFrequencyEditText.setText(data.cardiacFrequency.toString())
        beatTypeRadiogroup.check(data.beatType)
        arteriousPressureEditText.setText(data.bloodPressure.toString())
        capillarFillingTimeRadioGroup.check(data.capillarFillTime)
        mucousSkinColourRadiogroup.check(data.mucousSkinColor)
        eyesOpeningSpinner.setSelection(data.eyesOpening)
        verbalResponseSpinner.setSelection(data.verbalResponse)
        motorResponseSpinner.setSelection(data.motorResponse)
        pupilSxRadiogroup.check(data.pupilSx)
        pupilDXRadiogroup.check(data.pupilDx)
        photoreagentSxSwitch.isChecked = data.photoreagentSx
        photoreagentDxSwitch.isChecked = data.photoreagentDx
        bodyTempEditText.setText(data.temperature.toString())
        val siSipaValue = cardiacFrequencyEditText.text.toString().toDouble() / arteriousPressureEditText.text.toString().toDouble()
        siSipa.text = "SI/SIPA = $siSipaValue"
        if (siSipaValue > 0.9)
            siSipa.visibility = View.VISIBLE
        else
            siSipa.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    protected open fun calculateGCS(): Int {
        return 4 - eyesOpeningSpinner.selectedItemPosition +
                5 - motorResponseSpinner.selectedItemPosition +
                6 - verbalResponseSpinner.selectedItemPosition
    }

    companion object {

        @JvmStatic
        fun newInstance(data: VitalParametersData) = HistoryVitalParametersDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }
}