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
    protected lateinit var closedButton: Button
    protected lateinit var piercingButton: Button
    protected lateinit var helmetBeltSwitch: Switch
    protected lateinit var hemorrageSwitch: Switch
    protected lateinit var airwaysSwitch: Switch
    protected lateinit var tachipneaDyspneaSwitch: Switch
    protected lateinit var voletSwitch: Switch
    protected lateinit var positiveEcofastButton: Button
    protected lateinit var negativeEcofastButton: Button
    protected lateinit var unstablePelvisSwitch: Switch
    protected lateinit var amputationSwitch: Switch
    protected lateinit var sunkenSkullButton: Button
    protected lateinit var otorrhagiaButton: Button
    protected lateinit var paraparesisButton: Button
    protected lateinit var tetraparesisButton: Button
    protected lateinit var paresthesiaButton: Button

    protected lateinit var phyisiologicButton: Button
    protected lateinit var anatomicButton: Button
    protected lateinit var dynamicButton: Button
    protected lateinit var clinicalJudgmentButton: Button
    protected lateinit var shockIndexText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_patient_status_dialog, container, false)

        getComponents(root)

        setData(arguments!!.get("data") as PatientStatusData)

        val exitButton = root.findViewById<ImageButton>(R.id.patient_image_button)
        exitButton.setOnClickListener {
            // Here you can save eventual modifications to the history element
            dialog!!.cancel()
        }

        return root
    }

    protected open fun getComponents(root: View) {
        root.apply {
            hemorrageSwitch = findViewById(R.id.emorragia_esterna_switch)
            airwaysSwitch = findViewById(R.id.vie_aeree_switch)
            tachipneaDyspneaSwitch = findViewById(R.id.tachipnea_switch)
            helmetBeltSwitch = findViewById(R.id.casco_cintura_switch)
            voletSwitch = findViewById(R.id.volet_switch)
            positiveEcofastButton = findViewById(R.id.positivo_button)
            negativeEcofastButton = findViewById(R.id.negativo_button)
            unstablePelvisSwitch = findViewById(R.id.bacino_instabile_switch)
            amputationSwitch = findViewById(R.id.amputazione_switch)
            sunkenSkullButton = findViewById(R.id.infossata_button)
            otorrhagiaButton = findViewById(R.id.otorragia_button)
            paraparesisButton = findViewById(R.id.paraparesi_button)
            tetraparesisButton = findViewById(R.id.tetraparesi_button)
            paresthesiaButton = findViewById(R.id.parestesia_button)

            phyisiologicButton = findViewById(R.id.fisiologico_button)
            anatomicButton = findViewById(R.id.anatomico_button)
            dynamicButton = findViewById(R.id.dinamico_button)
            clinicalJudgmentButton = findViewById(R.id.giudizio_clinico_button)

            shockIndexText = findViewById(R.id.shock_index_text)
            piercingButton = findViewById(R.id.penetrante_button)
            closedButton = findViewById(R.id.chiuso_button)
        }
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    protected open fun setData(data: PatientStatusData) {
        if (data.traumaChiuso) {
            ButtonAppearance.activateButton(closedButton, resources)
        }
        if (data.traumaPenetrante) {
            ButtonAppearance.activateButton(piercingButton, resources)
        }

        helmetBeltSwitch.isChecked = data.cascoCintura

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