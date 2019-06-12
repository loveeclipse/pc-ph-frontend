package it.unibo.preh_frontend.dialog


import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PatientStatusData


class VitalParametersDialogFragment : DialogFragment() {
    private lateinit var vieAeree : RadioGroup
    private lateinit var freqRespiratoria : Spinner
    private lateinit var saturazione : EditText
    private lateinit var freqCaridaca: EditText
    private lateinit var tipoBattito : RadioGroup
    private lateinit var presArteriosa : EditText
    private lateinit var tempRiempCapillare : RadioGroup
    private lateinit var colorCuteMucose : RadioGroup
    private lateinit var aperturaOcchi : Spinner
    private lateinit var rispostaVerbale : Spinner
    private lateinit var rispostaMotoria : Spinner
    private lateinit var pupilleSx : RadioGroup
    private lateinit var pupilleDx : RadioGroup
    private lateinit var fotoreagenteSx : Switch
    private lateinit var fotoreagenteDx : Switch
    private lateinit var tempCorporea : EditText

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog

    private var saveState = PatientStatusData()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        /*parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)

        vieAeree = root.findViewById(R.id.vie_aeree_switch)

        freqRespiratoria = root.findViewById(R.id.freq_resp_spinner)

        saturazione = root.findViewById(R.id.saturazione_edittext)

        freqCaridaca = root.findViewById(R.id.freq_cardiaca_edittext)

        tipoBattito = root.findViewById(R.id.tipo_battito_radiogroup)

        presArteriosa = root.findViewById(R.id.pres_arter_edittext)

        tempRiempCapillare = root.findViewById(R.id.riempimento_capillare_radiogroup)

        colorCuteMucose = root.findViewById(R.id.cute_mucose_radiogroup)

        aperturaOcchi = root.findViewById(R.id.apertura_occhi_spinner)

        rispostaVerbale = root.findViewById(R.id.risposta_verbale_spinner)

        rispostaMotoria = root.findViewById(R.id.risposta_motoria_spinner)

        pupilleSx = root.findViewById(R.id.pupilleSx_radiogroup)

        pupilleDx = root.findViewById(R.id.pupilleDx_radiogroup)

        fotoreagenteSx = root.findViewById(R.id.fotoreagenteSx_switch)

        fotoreagenteDx = root.findViewById(R.id.fotoreagenteDx_switch)

        tempCorporea = root.findViewById(R.id.temp_corporea_edittext)



        val saveAndExitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        saveAndExitButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setMessage("Vuoi uscire senza salvare?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                    "Si"
            ) { dialog, _ ->
                dialog.cancel()
                parentDialog.dismiss()
            }
            builder1.setNegativeButton(
                    "No"
            ) { dialog, _ -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }*/
        val respiratoryFrequencySpinner = root.findViewById<Spinner>(R.id.freq_resp_spinner)
        val eyesOpeningSpinner = root.findViewById<Spinner>(R.id.apertura_occhi_spinner)
        val verbalResponseSpinner = root.findViewById<Spinner>(R.id.risposta_verbale_spinner)
        val motoryResponseSpinner = root.findViewById<Spinner>(R.id.risposta_motoria_spinner)

        var adapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.spinner_layout)
        respiratoryFrequencySpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.eyeOpeningItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.spinner_layout)
        eyesOpeningSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.verbalResponseItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.spinner_layout)
        verbalResponseSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.motorResponseItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.spinner_layout)
        motoryResponseSpinner.adapter = adapter

        return root
    }


    override fun onCancel(dialog: DialogInterface) {
        //SALVA PARAMETRI VITALI
        dialog.cancel()
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10,height)
    }
}