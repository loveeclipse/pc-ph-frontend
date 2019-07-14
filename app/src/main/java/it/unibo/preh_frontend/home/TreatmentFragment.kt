package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.dialog.IppvDialogFragment
import it.unibo.preh_frontend.model.TreatmentHistoryData
import it.unibo.preh_frontend.utils.PhysiologicaCriteriaManager
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.utils.RetrofitClient
import it.unibo.preh_frontend.model.dt_model.SimpleTreatment
import it.unibo.preh_frontend.model.dt_model.InjectionTreatment
import it.unibo.preh_frontend.utils.DateManager

class TreatmentFragment : Fragment() {

    private val ADD_OPTION: String = "Registrato"
    private val REMOVE_OPTION: String = "Annullato"

    private lateinit var adrenalinButton: Button
    private lateinit var shockButton: Button
    private lateinit var resuscitationButton: Button

    private lateinit var jawSubluxationButton: Button
    private lateinit var guedelButton: Button
    private lateinit var cricothyrotomyButton: Button
    private lateinit var trachealTubeButton: Button

    private lateinit var oxygenTherapyButton: Button
    private lateinit var ambuButton: Button
    private lateinit var minithoracotomySxButton: Button
    private lateinit var minithoracotomyDxButton: Button
    private lateinit var ippvButton: Button

    private lateinit var peripheralSpinner: Spinner
    private lateinit var centralSpinner: Spinner
    private lateinit var intraosseousSpinner: Spinner
    private lateinit var peripheralButton: Button
    private lateinit var centralButton: Button
    private lateinit var intraosseousButton: Button
    private lateinit var hemostasisButton: Button
    private lateinit var pelvicBlinderButton: Button
    private lateinit var tourniquetButton: Button
    private lateinit var reboaArea1Button: Button
    private lateinit var reboaArea3Button: Button

    private lateinit var neuroprotectionButton: Button
    private lateinit var thermalProtectionButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_treatment, container, false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)
        initSpinner()

        adrenalinButton.setOnClickListener {
            if (!resuscitationButton.isActivated)
                activeAndChangeButton(resuscitationButton, R.string.termina_rianimazione, R.string.inizio_rianimazione)
            addHistoryEntry(adrenalinButton.isPressed, "", this.getString(R.string.andrenalina_1mg))
        }
        shockButton.setOnClickListener {
            if (!resuscitationButton.isActivated)
                activeAndChangeButton(resuscitationButton, R.string.termina_rianimazione, R.string.inizio_rianimazione)
            addHistoryEntry(shockButton.isPressed, "count", this.getString(R.string.esegui_shock))
        }
        resuscitationButton.setOnClickListener {
            if (!resuscitationButton.isActivated)
                activeAndChangeButton(resuscitationButton, R.string.termina_rianimazione, R.string.inizio_rianimazione)
            else
                deactivateAndChangeButton(resuscitationButton, R.string.inizio_rianimazione, R.string.termina_rianimazione)
        }

        jawSubluxationButton.setOnClickListener {
            setButtonColor(jawSubluxationButton, resources, R.string.sublussazione_mandibola)
        }
        guedelButton.setOnClickListener {
            setButtonColor(guedelButton, resources, R.string.guedel)
        }
        cricothyrotomyButton.setOnClickListener {
            setButtonColor(cricothyrotomyButton, resources, R.string.crico_tirotomia)
        }
        trachealTubeButton.setOnClickListener {
            setButtonColor(trachealTubeButton, resources, R.string.tubo_tracheale)
            val time = DateManager.getStandardRepresentation()
            if (trachealTubeButton.isActivated) {
                PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                        this.getString(R.string.necessit_supporto_ventilatorio)).activeCentralization()
                RetrofitClient.postSimpleTreatment(SimpleTreatment("tracheal-tube", time))
            } else {
                PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext(),
                        this.getString(R.string.necessit_supporto_ventilatorio)).deactivatesCentralization()
                RetrofitClient.deleteSimpleManeuver("tracheal-tube")
            }
        }

        oxygenTherapyButton.setOnClickListener {
            addHistoryEntry(oxygenTherapyButton.isPressed, "", this.getString(R.string.ossigenoterapia_i_min))
        }
        ambuButton.setOnClickListener {
            addHistoryEntry(ambuButton.isPressed, "", this.getString(R.string.ambu))
        }
        minithoracotomySxButton.setOnClickListener {
            setButtonColor(minithoracotomySxButton, resources, R.string.minithoracotomySx)
        }
        minithoracotomyDxButton.setOnClickListener {
            setButtonColor(minithoracotomyDxButton, resources, R.string.minithoracotomyDx)
        }
        ippvButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("fragment_ippv_dialog") == null)
                IppvDialogFragment().show(requireActivity().supportFragmentManager, "fragment_ippv_dialog")
        }

        peripheralSpinner.onItemSelectedListener = spinnerAdapter(peripheralButton)
        peripheralButton.setOnClickListener {
            addHistoryEntry(peripheralButton.isPressed, peripheralSpinner.selectedItem.toString(),
                    "${peripheralSpinner.selectedItem} ${this.getString(R.string.gauge)} ${this.getString(R.string.periferica)}")
            val time = DateManager.getStandardRepresentation()
            val peripheralInjection = InjectionTreatment("peripheral", peripheralSpinner.selectedItem.toString() + "-" + this.getString(R.string.gauge), time)
            RetrofitClient.postInjectionTreatment(peripheralInjection)
            peripheralSpinner.setSelection(0)
        }
        centralSpinner.onItemSelectedListener = spinnerAdapter(centralButton)
        centralButton.setOnClickListener {
            addHistoryEntry(centralButton.isPressed, centralSpinner.selectedItem.toString(),
                    "${centralSpinner.selectedItem} ${this.getString(R.string.french)} ${this.getString(R.string.centrale)}")
            val time = DateManager.getStandardRepresentation()
            val centralInjection = InjectionTreatment("central", centralSpinner.selectedItem.toString() + "-" + this.getString(R.string.french), time)
            RetrofitClient.postInjectionTreatment(centralInjection)
            centralSpinner.setSelection(0)
        }
        intraosseousSpinner.onItemSelectedListener = spinnerAdapter(intraosseousButton)
        intraosseousButton.setOnClickListener {
            addHistoryEntry(intraosseousButton.isPressed, intraosseousSpinner.selectedItem.toString(),
                    "${intraosseousSpinner.selectedItem} ${this.getString(R.string.size)} ${this.getString(R.string.intraossea)}")
            val time = DateManager.getStandardRepresentation()
            val intraosseusInjection = InjectionTreatment("intreosseus", intraosseousSpinner.selectedItem.toString() + "-" + this.getString(R.string.size), time)
            RetrofitClient.postInjectionTreatment(intraosseusInjection)
            intraosseousSpinner.setSelection(0)
        }
        hemostasisButton.setOnClickListener {
            addHistoryEntry(hemostasisButton.isPressed, "", this.getString(R.string.emostasi))
        }
        pelvicBlinderButton.setOnClickListener {
            addHistoryEntry(pelvicBlinderButton.isPressed, "", this.getString(R.string.pelvic_binder))
        }
        tourniquetButton.setOnClickListener {
            if (!tourniquetButton.isActivated)
                activeAndChangeButton(tourniquetButton, R.string.termina_tourniquet, R.string.inizia_tourniquet)
            else
                deactivateAndChangeButton(tourniquetButton, R.string.inizia_tourniquet, R.string.termina_tourniquet)
        }
        reboaArea1Button.setOnClickListener {
            if (!reboaArea1Button.isActivated)
                activeAndChangeButton(reboaArea1Button, R.string.termina_reboa_zona_1, R.string.inizia_reboa_zona_1)
            else
                deactivateAndChangeButton(reboaArea1Button, R.string.inizia_reboa_zona_1, R.string.termina_reboa_zona_1)
        }
        reboaArea3Button.setOnClickListener {
            if (!reboaArea3Button.isActivated)
                activeAndChangeButton(reboaArea3Button, R.string.termina_reboa_zona_3, R.string.inizia_reboa_zona_3)
            else
                deactivateAndChangeButton(reboaArea3Button, R.string.inizia_reboa_zona_3, R.string.termina_reboa_zona_3)
        }

        neuroprotectionButton.setOnClickListener {
            addHistoryEntry(neuroprotectionButton.isPressed, "", this.getString(R.string.neuroprotezione))
        }
        thermalProtectionButton.setOnClickListener {
            addHistoryEntry(thermalProtectionButton.isPressed, "", this.getString(R.string.protezione_termica))
        }
        return root
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("TreatmentData", null), TreatmentData::class.java)
        if (savedState != null) {
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun addHistoryEntry(
        treatmentBooleanValue: Boolean?,
        treatmentStringValue: String?,
        treatmentName: String,
        option: String = ADD_OPTION
    ) {
        val treatmentData = TreatmentHistoryData(
                treatmentBooleanValue,
                treatmentStringValue,
                "$option $treatmentName"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ComplicationsHistoryData", Gson().toJson(treatmentData)).apply()
        HistoryManager.addEntry(treatmentData, sharedPreferences)
    }

    private fun setHistoryStatus(treatmentBooleanValue: Boolean?, treatmentStringValue: String?, maneuverName: String) {
        when {
            treatmentBooleanValue!! ->
                addHistoryEntry(treatmentBooleanValue, treatmentStringValue, maneuverName, ADD_OPTION)
            else ->
                addHistoryEntry(treatmentBooleanValue, treatmentStringValue, maneuverName, REMOVE_OPTION)
        }
    }

    private fun getComponents(root: View) {
        root.apply {
            adrenalinButton = findViewById(R.id.adrenaline_button)
            shockButton = findViewById(R.id.shock_button)
            resuscitationButton = findViewById(R.id.cpr_button)

            jawSubluxationButton = findViewById(R.id.subluxation_button)
            guedelButton = findViewById(R.id.guedel_button)
            cricothyrotomyButton = findViewById(R.id.tirotomy_button)
            trachealTubeButton = findViewById(R.id.tracheal_tube_button)

            oxygenTherapyButton = findViewById(R.id.oxigen_therapy_button)
            ambuButton = findViewById(R.id.ambu_button)
            minithoracotomySxButton = findViewById(R.id.minitoracotomiaSx_button)
            minithoracotomyDxButton = findViewById(R.id.minithoracotomyDx_button)
            ippvButton = findViewById(R.id.ippv_button)

            peripheralSpinner = findViewById(R.id.peripheric_spinner)
            centralSpinner = findViewById(R.id.central_spinner)
            intraosseousSpinner = findViewById(R.id.intraosseous_spinner)
            peripheralButton = findViewById(R.id.peripheric_button)
            centralButton = findViewById(R.id.central_button)
            intraosseousButton = findViewById(R.id.intraosseous_button)
            hemostasisButton = findViewById(R.id.emostasis_button)
            pelvicBlinderButton = findViewById(R.id.pelvicbind_button)
            tourniquetButton = findViewById(R.id.tourniquet_button)
            reboaArea1Button = findViewById(R.id.reboaZ1_button)
            reboaArea3Button = findViewById(R.id.reboaZ3_button)

            neuroprotectionButton = findViewById(R.id.neuroprotection_button)
            thermalProtectionButton = findViewById(R.id.termic_protection_button)
        }
    }

    private fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gaugeSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        peripheralSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.frenchSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        centralSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.sizeSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        intraosseousSpinner.adapter = newAdapter
    }

    private fun spinnerAdapter(button: Button) = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            button.isEnabled = (position != 0)
        }
    }

    private fun activeAndChangeButton(button: Button, buttonSetText: Int, buttonText: Int) {
        button.isActivated = true
        activateButton(button, resources)
        button.text = this.getString(buttonSetText)
        addHistoryEntry(button.isActivated, "", this.getString(buttonText))
    }

    private fun deactivateAndChangeButton(button: Button, buttonSetText: Int, buttonText: Int) {
        button.isActivated = false
        deactivateButton(button, resources)
        button.text = this.getString(buttonSetText)
        addHistoryEntry(button.isActivated, "", this.getString(buttonText))
    }

    private fun setButtonColor(button: Button, resources: Resources, buttonText: Int) {
        if (!button.isActivated) {
            button.isActivated = true
            activateButton(button, resources)
            setHistoryStatus(button.isActivated, "", this.getString(buttonText))
        } else {
            button.isActivated = false
            deactivateButton(button, resources)
            setHistoryStatus(button.isActivated, "", this.getString(buttonText))
        }
    }

    private fun applySharedPreferences(treatmentData: TreatmentData) {
        resuscitationButton.isActivated = treatmentData.resuscitation
        if (treatmentData.resuscitation) {
            resuscitationButton.isActivated = true
            activateButton(resuscitationButton, resources)
            resuscitationButton.text = this.getString(R.string.termina_tourniquet)
        }
        jawSubluxationButton.isActivated = treatmentData.jawSubluxation
        if (treatmentData.jawSubluxation) {
            jawSubluxationButton.isActivated = true
            activateButton(jawSubluxationButton, resources)
        }
        guedelButton.isActivated = treatmentData.guedel
        if (treatmentData.guedel) {
            guedelButton.isActivated = true
            activateButton(guedelButton, resources)
        }
        cricothyrotomyButton.isActivated = treatmentData.cricothyrotomy
        if (treatmentData.cricothyrotomy) {
            cricothyrotomyButton.isActivated = true
            activateButton(cricothyrotomyButton, resources)
        }
        trachealTubeButton.isActivated = treatmentData.trachealTube
        if (treatmentData.trachealTube) {
            trachealTubeButton.isActivated = true
            activateButton(trachealTubeButton, resources)
        }
        minithoracotomySxButton.isActivated = treatmentData.minithoracotomySx
        if (treatmentData.minithoracotomySx) {
            minithoracotomySxButton.isActivated = true
            activateButton(minithoracotomySxButton, resources)
        }
        minithoracotomyDxButton.isActivated = treatmentData.minithoracotomyDx
        if (treatmentData.minithoracotomyDx) {
            minithoracotomyDxButton.isActivated = true
            activateButton(minithoracotomyDxButton, resources)
        }
        tourniquetButton.isActivated = treatmentData.tourniquet
        if (treatmentData.tourniquet) {
            tourniquetButton.isActivated = true
            activateButton(tourniquetButton, resources)
            tourniquetButton.text = this.getString(R.string.termina_tourniquet)
        }
        reboaArea1Button.isActivated = treatmentData.reboaArea1
        if (treatmentData.reboaArea1) {
            reboaArea1Button.isActivated = true
            activateButton(reboaArea1Button, resources)
            reboaArea1Button.text = this.getString(R.string.termina_reboa_zona_1)
        }
        reboaArea3Button.isActivated = treatmentData.reboaArea3
        if (treatmentData.reboaArea3) {
            reboaArea3Button.isActivated = true
            activateButton(reboaArea3Button, resources)
            reboaArea3Button.text = this.getString(R.string.termina_reboa_zona_3)
        }
    }

    fun getData(): TreatmentData {
        return TreatmentData(
                resuscitationButton.isActivated,
                jawSubluxationButton.isActivated,
                guedelButton.isActivated,
                cricothyrotomyButton.isActivated,
                trachealTubeButton.isActivated,
                minithoracotomySxButton.isActivated,
                minithoracotomyDxButton.isActivated,
                tourniquetButton.isActivated,
                reboaArea1Button.isActivated,
                reboaArea3Button.isActivated)
    }
}
