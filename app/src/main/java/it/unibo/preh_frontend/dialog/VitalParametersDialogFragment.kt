package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.VitalParametersData

class VitalParametersDialogFragment : DialogFragment() {
    private lateinit var vieAeree: RadioGroup
    private lateinit var freqRespiratoria: Spinner
    private lateinit var saturazione: EditText
    private lateinit var freqCaridaca: EditText
    private lateinit var tipoBattito: RadioGroup
    private lateinit var presArteriosa: EditText
    private lateinit var tempRiempCapillare: RadioGroup
    private lateinit var colorCuteMucose: RadioGroup
    private lateinit var aperturaOcchi: Spinner
    private lateinit var rispostaVerbale: Spinner
    private lateinit var rispostaMotoria: Spinner
    private lateinit var pupilleSx: RadioGroup
    private lateinit var pupilleDx: RadioGroup
    private lateinit var fotoreagenteSx: Switch
    private lateinit var fotoreagenteDx: Switch
    private lateinit var tempCorporea: EditText

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        parentDialog = dialog!!
        isCancelable = false
        dialog!!.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        vieAeree = root.findViewById(R.id.vieaeree_radiogroup)

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

        setSharedPreferences()

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        saveAndExitButton.setOnClickListener {
            if (!checkEveryField()) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Torna Indietro")
                builder.setMessage("Devi compilare ancora dei campi")
                builder.setCancelable(true)

                builder.setNeutralButton(
                        "Indietro"
                ) { dialog, _ ->
                    dialog.cancel()
                }

                val alert11 = builder.create()
                alert11.show()
            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Conferma Parametri Vitali")
                builder.setMessage("Vuoi confermare i parametri inseriti?")
                builder.setCancelable(true)

                builder.setPositiveButton(
                        "Si"
                ) { dialog, _ ->
                    dialog.cancel()
                    parentDialog.cancel()
                }
                builder.setNegativeButton(
                        "No"
                ) { dialog, _ -> dialog.cancel() }

                val alert11 = builder.create()
                alert11.show()
            }
        }
        return root
    }

    private fun checkEveryField(): Boolean {
        return (vieAeree.checkedRadioButtonId != -1 &&
                freqRespiratoria.selectedItemPosition != 0 &&
                saturazione.text.toString() != "" &&
                freqCaridaca.text.toString() != "" &&
                tipoBattito.checkedRadioButtonId != -1 &&
                presArteriosa.text.toString() != "" &&
                tempRiempCapillare.checkedRadioButtonId != -1 &&
                colorCuteMucose.checkedRadioButtonId != -1 &&
                aperturaOcchi.selectedItemPosition != 0 &&
                rispostaVerbale.selectedItemPosition != 0 &&
                rispostaMotoria.selectedItemPosition != 0 &&
                pupilleSx.checkedRadioButtonId != -1 &&
                pupilleDx.checkedRadioButtonId != -1 &&
                tempCorporea.text.toString() != "")
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("vitalParameters", null), VitalParametersData::class.java)
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    vieAeree.check(newSaveState.vieAeree)
                    freqRespiratoria.setSelection(newSaveState.frequenzaRespiratoria)
                    saturazione.setText(newSaveState.saturazionePeriferica.toString())
                    freqCaridaca.setText(newSaveState.frequenzaCaridaca.toString())
                    tipoBattito.check(newSaveState.tipoBattito)
                    presArteriosa.setText(newSaveState.pressioneArteriosa.toString())
                    tempRiempCapillare.check(newSaveState.tempoRiempimentoCapillare)
                    colorCuteMucose.check(newSaveState.coloritoCuteMucose)
                    aperturaOcchi.setSelection(newSaveState.aperturaOcchi)
                    rispostaVerbale.setSelection(newSaveState.rispostaVerbale)
                    rispostaMotoria.setSelection(newSaveState.rispostaMotoria)
                    pupilleSx.check(newSaveState.pupilleSx)
                    pupilleDx.check(newSaveState.pupilleDx)
                    fotoreagenteSx.isChecked = newSaveState.fotoreagenteSx
                    fotoreagenteDx.isChecked = newSaveState.fotoreagenteDx
                    tempCorporea.setText(newSaveState.temperature.toString())
                }
            }
        }).start()
    }

    override fun onCancel(dialog: DialogInterface) {
        Thread(Runnable {
            val saveState = VitalParametersData(vieAeree.checkedRadioButtonId,
                    freqRespiratoria.selectedItemPosition,
                    Integer.parseInt(saturazione.text.toString()),
                    Integer.parseInt(freqCaridaca.text.toString()),
                    tipoBattito.checkedRadioButtonId,
                    Integer.parseInt(presArteriosa.text.toString()),
                    tempRiempCapillare.checkedRadioButtonId,
                    colorCuteMucose.checkedRadioButtonId,
                    aperturaOcchi.selectedItemPosition,
                    rispostaVerbale.selectedItemPosition,
                    rispostaMotoria.selectedItemPosition,
                    pupilleSx.checkedRadioButtonId,
                    pupilleDx.checkedRadioButtonId,
                    fotoreagenteSx.isChecked,
                    fotoreagenteDx.isChecked,
                    Integer.parseInt(tempCorporea.text.toString())
            )
            val gson = Gson()
            val stateAsJson = gson.toJson(saveState)
            sharedPreferences.edit().putString("vitalParameters", stateAsJson).apply()
            super.onCancel(dialog)
        }).start()
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10, height)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}