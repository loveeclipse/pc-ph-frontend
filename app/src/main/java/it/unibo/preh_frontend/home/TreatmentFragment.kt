package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
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

class TreatmentFragment : Fragment() {

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

    private var subulussazioneIsActive = false
    private var guedelIsActive = false
    private var cricoTirotomiaIsActive = false
    private var tuboTrachealeIsActive = false
    private var minithoracotomySxIsActive = false
    private var minithoracotomyDxIsActive = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_treatment, container, false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        getComponents(root)
        initSpinner()
        trachealTubeButton.setOnClickListener {
            if (!tuboTrachealeIsActive) {
                addHistoryEntry(trachealTubeButton.isPressed, "", this.getString(R.string.tubo_tracheale))
                tuboTrachealeIsActive = true
                PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext()).activeCentralization()
                activateButton(trachealTubeButton, resources)
            } else {
                tuboTrachealeIsActive = false
                PhysiologicaCriteriaManager(sharedPreferences, requireActivity(), requireContext()).deactivatesCentralization()
                deactivateButton(trachealTubeButton, resources)
            }
        }
        ippvButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("fragment_ippv_dialog") == null)
                IppvDialogFragment().show(requireActivity().supportFragmentManager, "fragment_ippv_dialog")
        }
        peripheralSpinner.onItemSelectedListener = spinnerAdapter(peripheralSpinner, peripheralButton)
        peripheralButton.setOnClickListener {
            addHistoryEntry(peripheralButton.isPressed, peripheralSpinner.selectedItem.toString(), this.getString(R.string.periferica))
        }
        centralSpinner.onItemSelectedListener = spinnerAdapter(centralSpinner, centralButton)
        centralButton.setOnClickListener {
            addHistoryEntry(centralButton.isPressed, centralSpinner.selectedItem.toString(), this.getString(R.string.centrale))
        }
        intraosseousSpinner.onItemSelectedListener = spinnerAdapter(intraosseousSpinner, intraosseousButton)
        intraosseousButton.setOnClickListener {
            addHistoryEntry(intraosseousButton.isPressed, intraosseousSpinner.selectedItem.toString(), this.getString(R.string.centrale))
        }
        return root
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("treatmentData", null), TreatmentData::class.java)
        if (savedState != null) {
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun addHistoryEntry(treatmentBooleanValue: Boolean?, treatmentStringValue: String?, treatmentName: String) {
        val treatmentData = TreatmentHistoryData(
                treatmentBooleanValue,
                treatmentStringValue,
                "Effettuato $treatmentName"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ComplicationsHistoryData", Gson().toJson(treatmentData)).apply()
        HistoryManager.addEntry(treatmentData, sharedPreferences)
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

    private fun applySharedPreferences(savedState: TreatmentData) {
        // setta i bottoni a seconda del valore in savestate
        if (savedState.subluxation) {
            jawSubluxationButton.backgroundTintList = resources.getColorStateList(R.color.colorAccent)
        }
    }

    private fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.gaugeSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        peripheralSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.frenchSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        centralSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.sizeSpinnerItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        intraosseousSpinner.adapter = newAdapter
    }

    private fun spinnerAdapter(spinner: Spinner, button: Button) = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (spinner.selectedItem != "")
                button.isEnabled = true
        }
    }

    fun getData(): TreatmentData {
        return TreatmentData(
                subulussazioneIsActive,
                guedelIsActive,
                cricoTirotomiaIsActive,
                tuboTrachealeIsActive,
                minithoracotomySxIsActive,
                minithoracotomyDxIsActive)
    }
}
