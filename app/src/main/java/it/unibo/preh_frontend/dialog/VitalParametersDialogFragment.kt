package it.unibo.preh_frontend.dialog


import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.VitalParametersData


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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)


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



        val saveAndExitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        saveAndExitButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setTitle("Vuoi uscire senza salvare?")
            builder1.setMessage("Devi compilare ancora dei campi")
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
        }

        return root
    }

    private fun checkEveryField(): Boolean{
        return (vieAeree.checkedRadioButtonId != -1 &&
                freqRespiratoria.selectedItemPosition != null &&
                saturazione.text.toString() != "" &&
                freqCaridaca.text.toString() != "" &&
                tipoBattito.checkedRadioButtonId != -1 &&
                presArteriosa.text.toString() != "" &&
                tempRiempCapillare.checkedRadioButtonId != -1 &&
                colorCuteMucose.checkedRadioButtonId != -1 &&
                aperturaOcchi.selectedItemPosition != -1 &&
                rispostaVerbale.selectedItemPosition != null &&
                rispostaMotoria.selectedItemPosition != null &&
                pupilleSx.checkedRadioButtonId != -1 &&
                pupilleDx.checkedRadioButtonId != -1 &&
                tempCorporea.text.toString() != "")
    }


    override fun onCancel(dialog: DialogInterface) {
        //SALVA PARAMETRI VITALI

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
                                            Integer.parseInt(tempCorporea.text.toString())
                                            )
        val gson = Gson()
        val stateAsJson = gson.toJson(saveState)
        sharedPreferences.edit().putString("vitalParameters",stateAsJson).apply()
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