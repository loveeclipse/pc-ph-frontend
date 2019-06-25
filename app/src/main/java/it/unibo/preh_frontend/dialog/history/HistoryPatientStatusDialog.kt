package it.unibo.preh_frontend.dialog.history
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.utils.ButtonAppearance

open class HistoryPatientStatusDialog : DialogFragment() {
    protected lateinit var chiusoButton: Button
    protected lateinit var penetranteButton: Button
    protected lateinit var cascoCinturaSwitch: Switch
    protected lateinit var emorragiaSwitch: Switch
    protected lateinit var vieAereeSwitch: Switch
    protected lateinit var tachipneaSwitch: Switch
    protected lateinit var voletSwitch: Switch
    protected lateinit var positivoButton: Button
    protected lateinit var negativoButton: Button
    protected lateinit var bacinoInstabileSwitch: Switch
    protected lateinit var amputazioneSwitch: Switch
    protected lateinit var infossataButton: Button
    protected lateinit var otorragiaButton: Button
    protected lateinit var paraparesiButton: Button
    protected lateinit var tetraparesiButton: Button
    protected lateinit var parestesiaButton: Button

    protected lateinit var fisiologicoButton: Button
    protected lateinit var anatomicoButton: Button
    protected lateinit var dinamicoButton: Button
    protected lateinit var giudizioClinicoButton: Button
    protected lateinit var shockIndexText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)

        getComponents(root)

        setData(arguments?.get("data") as PatientStatusData)

        val exitButton = root.findViewById<ImageButton>(R.id.patient_image_button)
        exitButton.setOnClickListener {
            // Here you can save eventual modifications to the history element
            dialog?.cancel()
        }

        return root
    }

    protected open fun getComponents(root: View) {
        root.apply {
            emorragiaSwitch = findViewById(R.id.emorragia_esterna_switch)
            vieAereeSwitch = findViewById(R.id.vie_aeree_switch)
            tachipneaSwitch = findViewById(R.id.tachipnea_switch)
            cascoCinturaSwitch = findViewById(R.id.casco_cintura_switch)
            voletSwitch = findViewById(R.id.volet_switch)
            positivoButton = findViewById(R.id.positivo_button)
            negativoButton = findViewById(R.id.negativo_button)
            bacinoInstabileSwitch = findViewById(R.id.bacino_instabile_switch)
            amputazioneSwitch = findViewById(R.id.amputazione_switch)
            infossataButton = findViewById(R.id.infossata_button)
            otorragiaButton = findViewById(R.id.otorragia_button)
            paraparesiButton = findViewById(R.id.paraparesi_button)
            tetraparesiButton = findViewById(R.id.tetraparesi_button)
            parestesiaButton = findViewById(R.id.parestesia_button)

            fisiologicoButton = findViewById(R.id.fisiologico_button)
            anatomicoButton = findViewById(R.id.anatomico_button)
            dinamicoButton = findViewById(R.id.dinamico_button)
            giudizioClinicoButton = findViewById(R.id.giudizio_clinico_button)

            shockIndexText = findViewById(R.id.shock_index_text)
            penetranteButton = findViewById(R.id.penetrante_button)
            chiusoButton = findViewById(R.id.chiuso_button)
        }
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    protected open fun setData(data: PatientStatusData) {
        if (data.traumaChiuso) {
            ButtonAppearance.activateButton(chiusoButton, resources)
        }
        if (data.traumaPenetrante) {
            ButtonAppearance.activateButton(penetranteButton, resources)
        }

        cascoCinturaSwitch.isChecked = data.cascoCintura

        // TODO Add the other data
    }

    companion object {

        @JvmStatic
        fun newInstance(data: PatientStatusData) = HistoryPatientStatusDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }
}