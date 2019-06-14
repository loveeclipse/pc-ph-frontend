package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton

class PatientStatusDialogFragment : DialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog
    private lateinit var chiusoButton: Button
    private lateinit var penetranteButton: Button
    private lateinit var cascoCinturaSwitch: Switch
    private lateinit var emorragiaButton: Button
    private lateinit var vieAereeSwitch: Switch
    private lateinit var tachipneaButton: Button
    private lateinit var voletButton: Button
    private lateinit var positivoButton: Button
    private lateinit var negativoButton: Button
    private lateinit var bacinoInstabileButton: Button
    private lateinit var amputazioneButton: Button
    private lateinit var infossataButton: Button
    private lateinit var otorragiaButton: Button
    private lateinit var paraparesiButton: Button
    private lateinit var tetraparesiButton: Button
    private lateinit var parestesiaButton: Button

    private lateinit var fisiologicoButton: Button
    private lateinit var anatomicoButton: Button
    private lateinit var dinamicoButton: Button
    private lateinit var giudizioClinicoButton: Button
    private lateinit var shockIndexText: TextView

    private var saveState = PatientStatusData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)

        chiusoButton = root.findViewById(R.id.chiuso_button)
        chiusoButton.setOnClickListener {
            if (this.saveState.traumaChiuso) {
                deactivateButton(chiusoButton, resources)
                this.saveState.traumaChiuso = false
            } else {
                activateButton(chiusoButton, resources)
                this.saveState.traumaChiuso = true
            }
        }
        penetranteButton = root.findViewById(R.id.penetrante_button)
        penetranteButton.setOnClickListener {
            if (this.saveState.traumaPenetrante) {
                deactivateButton(penetranteButton, resources)
                this.saveState.traumaPenetrante = false
            } else {
                activateButton(penetranteButton, resources)
                this.saveState.traumaPenetrante = true
            }
        }

        cascoCinturaSwitch = root.findViewById(R.id.casco_cintura_switch)
        cascoCinturaSwitch.setOnCheckedChangeListener { _, state ->
            this.saveState.cascoCintura = state
        }

        emorragiaButton = root.findViewById(R.id.emorragia_esterna_switch)
        vieAereeSwitch = root.findViewById(R.id.vie_aeree_switch)
        tachipneaButton = root.findViewById(R.id.tachipnea_switch)
        voletButton = root.findViewById(R.id.volet_switch)
        positivoButton = root.findViewById(R.id.positivo_button)
        negativoButton = root.findViewById(R.id.negativo_button)
        bacinoInstabileButton = root.findViewById(R.id.bacino_instabile_switch)
        amputazioneButton = root.findViewById(R.id.amputazione_switch)
        infossataButton = root.findViewById(R.id.infossata_button)
        otorragiaButton = root.findViewById(R.id.otorragia_button)
        paraparesiButton = root.findViewById(R.id.paraparesi_button)
        tetraparesiButton = root.findViewById(R.id.tetraparesi_button)
        parestesiaButton = root.findViewById(R.id.parestesia_button)

        fisiologicoButton = root.findViewById(R.id.fisiologico_button)
        anatomicoButton = root.findViewById(R.id.anatomico_button)
        dinamicoButton = root.findViewById(R.id.dinamico_button)
        giudizioClinicoButton = root.findViewById(R.id.giudizio_clinico_button)

        shockIndexText = root.findViewById(R.id.shock_index_text)

        setSharedPreferences()

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.patient_image_button)
        saveAndExitButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setTitle("Vuoi uscire senza salvare?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                    "Sì"
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

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10, height)
    }

    override fun onCancel(dialog: DialogInterface) {
        Thread(Runnable {
            val gson = Gson()
            val stateAsJson = gson.toJson(saveState)
            sharedPreferences.edit().putString("patientState", stateAsJson).apply()
            super.onCancel(dialog)
        })
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("patientState", null), PatientStatusData::class.java)
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    if (newSaveState.traumaChiuso) activateButton(chiusoButton, resources)
                    if (newSaveState.traumaPenetrante) activateButton(penetranteButton, resources)

                    cascoCinturaSwitch.isChecked = newSaveState.cascoCintura
                }
                saveState = newSaveState
            } else {
                saveState = PatientStatusData()
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
