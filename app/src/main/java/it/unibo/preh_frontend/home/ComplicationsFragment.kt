package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ComplicationsData
import it.unibo.preh_frontend.model.ComplicationsHistoryData
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.utils.ButtonAppearance

class ComplicationsFragment : Fragment() {

    private lateinit var cardioCirculatoryArrestSwitch: Switch
    private lateinit var deterioratingStateConsciousnessSwitch: Switch
    private lateinit var anisoMidriasiSwitch: Switch
    private lateinit var respiratoryFailureSwitch: Switch
    private lateinit var cardioCirculatoryShockSwitch: Switch
    private lateinit var landingInItinereSwitch: Switch
    private lateinit var deathInItinereButton: Button
    private lateinit var deathArrivalInPSButton: Button

    private var itinereActive = false
    private var arrivalInPSActive = false

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_complications, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        cardioCirculatoryArrestSwitch = root.findViewById(R.id.cardio_circulatory_arrest_switch)
        cardioCirculatoryArrestSwitch.setOnClickListener {
            if (cardioCirculatoryArrestSwitch.isChecked) addHistoryEntry(cardioCirculatoryArrestSwitch.isChecked, this.getString(R.string.arresto_cardio_circolatorio))
        }
        deterioratingStateConsciousnessSwitch = root.findViewById(R.id.deteriorating_state_consciousness_switch)
        deterioratingStateConsciousnessSwitch.setOnClickListener {
            if (deterioratingStateConsciousnessSwitch.isChecked) addHistoryEntry(deterioratingStateConsciousnessSwitch.isChecked, this.getString(R.string.deterioramento_stato_di_coscenza))
        }
        anisoMidriasiSwitch = root.findViewById(R.id.aniso_midriasi_switch)
        anisoMidriasiSwitch.setOnClickListener {
            if (anisoMidriasiSwitch.isChecked) addHistoryEntry(anisoMidriasiSwitch.isChecked, this.getString(R.string.anisocoria_midriasi))
        }
        respiratoryFailureSwitch = root.findViewById(R.id.respiratory_failure_switch)
        respiratoryFailureSwitch.setOnClickListener {
            if (respiratoryFailureSwitch.isChecked) addHistoryEntry(respiratoryFailureSwitch.isChecked, this.getString(R.string.insufficienza_respiratoria))
        }
        cardioCirculatoryShockSwitch = root.findViewById(R.id.cardiocirculatory_shock_switch)
        cardioCirculatoryShockSwitch.setOnClickListener {
            if (cardioCirculatoryShockSwitch.isChecked) addHistoryEntry(cardioCirculatoryShockSwitch.isChecked, this.getString(R.string.shock_cardiocircolatorio))
        }
        landingInItinereSwitch = root.findViewById(R.id.landing_in_itinere_switch)
        landingInItinereSwitch.setOnClickListener {
            if (landingInItinereSwitch.isChecked) addHistoryEntry(landingInItinereSwitch.isChecked, this.getString(R.string.atterraggio_in_itinere_per_manovra_terapeutica))
        }
        deathInItinereButton = root.findViewById(R.id.itinere_button)
        deathInItinereButton.setOnClickListener {
            ButtonAppearance.activateButton(deathInItinereButton, resources)
            deathInItinereButton.isEnabled = false
            deathArrivalInPSButton.isEnabled = false
            if (deathInItinereButton.isPressed) addHistoryEntry(deathInItinereButton.isPressed, "${this.getString(R.string.decesso)} ${this.getString(R.string.in_itinere)}")
        }
        deathArrivalInPSButton = root.findViewById(R.id.arrival__in_ps_button)
        deathArrivalInPSButton.setOnClickListener {
            ButtonAppearance.activateButton(deathArrivalInPSButton, resources)
            deathArrivalInPSButton.isEnabled = false
            deathInItinereButton.isEnabled = false
            if (deathArrivalInPSButton.isPressed) addHistoryEntry(deathArrivalInPSButton.isPressed, "${this.getString(R.string.decesso)} ${this.getString(R.string.all_arrivo_in_ps)}")
        }
        return root
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("complicationsData", null), ComplicationsData::class.java)
        if (savedState != null) {
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    private fun addHistoryEntry(complicationsValue: Boolean, complicationsName: String) {
        val complicationsData = ComplicationsHistoryData(
                complicationsValue,
                complicationsName
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ComplicationsHistoryData", Gson().toJson(complicationsData)).apply()
        HistoryManager.addEntry(complicationsData, sharedPreferences)
    }

    private fun applySharedPreferences(savedState: ComplicationsData) {
        cardioCirculatoryArrestSwitch.isChecked = savedState.cardioCirculatoryArrest
        deterioratingStateConsciousnessSwitch.isChecked = savedState.deterioratingStateConsciousness
        anisoMidriasiSwitch.isChecked = savedState.anisoMidriasi
        respiratoryFailureSwitch.isChecked = savedState.respiratoryFailure
        cardioCirculatoryShockSwitch.isChecked = savedState.cardioCirculatoryShock
        landingInItinereSwitch.isChecked = savedState.landingInItinere
        if (savedState.deathInItinere) {
            // cambia colore a itinerebutton come attivo e disattiva deathArrivalInPSButton
        }
        if (savedState.deathInPs) {
            // cambia colore a deathArrivalInPSButton come attivo e disattiva itinerebutton
        }
    }

    fun getData(): ComplicationsData {
        return ComplicationsData(
                cardioCirculatoryArrestSwitch.isChecked,
                deterioratingStateConsciousnessSwitch.isChecked,
                anisoMidriasiSwitch.isChecked,
                respiratoryFailureSwitch.isChecked,
                cardioCirculatoryShockSwitch.isChecked,
                landingInItinereSwitch.isChecked,
                itinereActive,
                arrivalInPSActive)
    }
}
