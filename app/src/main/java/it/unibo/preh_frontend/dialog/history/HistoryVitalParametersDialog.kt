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
    protected lateinit var vieAeree: RadioGroup
    protected lateinit var freqRespiratoria: Spinner
    protected lateinit var saturazione: EditText
    protected lateinit var freqCaridaca: EditText
    protected lateinit var tipoBattito: RadioGroup
    protected lateinit var presArteriosa: EditText
    protected lateinit var tempRiempCapillare: RadioGroup
    protected lateinit var colorCuteMucose: RadioGroup
    protected lateinit var aperturaOcchi: Spinner
    protected lateinit var rispostaVerbale: Spinner
    protected lateinit var rispostaMotoria: Spinner
    protected lateinit var pupilleSx: RadioGroup
    protected lateinit var pupilleDx: RadioGroup
    protected lateinit var fotoreagenteSx: Switch
    protected lateinit var fotoreagenteDx: Switch
    protected lateinit var tempCorporea: EditText
    protected lateinit var gcsTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)

        getComponents(root)

        initSpinner()

        setData(arguments!!.get("data") as VitalParametersData)

        val exitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        exitButton.setOnClickListener {
                // Here you can save eventual modifications to the history element
                dialog!!.cancel()
            }

        return root
    }

    protected open fun getComponents(root: View) {
        root.apply {
            vieAeree = findViewById(R.id.vieaeree_radiogroup)
            freqRespiratoria = findViewById(R.id.freq_resp_spinner)
            saturazione = findViewById(R.id.saturazione_edittext)
            freqCaridaca = findViewById(R.id.freq_cardiaca_edittext)
            tipoBattito = findViewById(R.id.tipo_battito_radiogroup)
            presArteriosa = findViewById(R.id.pres_arter_edittext)
            tempRiempCapillare = findViewById(R.id.riempimento_capillare_radiogroup)
            colorCuteMucose = findViewById(R.id.cute_mucose_radiogroup)
            aperturaOcchi = findViewById(R.id.apertura_occhi_spinner)
            rispostaVerbale = findViewById(R.id.risposta_verbale_spinner)
            rispostaMotoria = findViewById(R.id.risposta_motoria_spinner)
            pupilleSx = findViewById(R.id.pupilleSx_radiogroup)
            pupilleDx = findViewById(R.id.pupilleDx_radiogroup)
            fotoreagenteSx = findViewById(R.id.fotoreagenteSx_switch)
            fotoreagenteDx = findViewById(R.id.fotoreagenteDx_switch)
            tempCorporea = findViewById(R.id.temp_corporea_edittext)
            gcsTextView = findViewById(R.id.gcs_textview)
        }
    }

    protected open fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        freqRespiratoria.apply {
            adapter = newAdapter
            setSelection(1)
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.eyeOpeningItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        aperturaOcchi.apply {
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
        rispostaVerbale.apply {
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
        rispostaMotoria.apply {
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
        vieAeree.check(data.vieAeree)
        freqRespiratoria.setSelection(data.frequenzaRespiratoria)
        saturazione.setText(data.saturazionePeriferica.toString())
        freqCaridaca.setText(data.frequenzaCaridaca.toString())
        tipoBattito.check(data.tipoBattito)
        presArteriosa.setText(data.pressioneArteriosa.toString())
        tempRiempCapillare.check(data.tempoRiempimentoCapillare)
        colorCuteMucose.check(data.coloritoCuteMucose)
        aperturaOcchi.setSelection(data.aperturaOcchi)
        rispostaVerbale.setSelection(data.rispostaVerbale)
        rispostaMotoria.setSelection(data.rispostaMotoria)
        pupilleSx.check(data.pupilleSx)
        pupilleDx.check(data.pupilleDx)
        fotoreagenteSx.isChecked = data.fotoreagenteSx
        fotoreagenteDx.isChecked = data.fotoreagenteDx
        tempCorporea.setText(data.temperature.toString())
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    protected open fun calculateGCS(): Int {
        return 4 - aperturaOcchi.selectedItemPosition +
                5 - rispostaMotoria.selectedItemPosition +
                6 - rispostaVerbale.selectedItemPosition
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