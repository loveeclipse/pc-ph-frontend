package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
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
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton

class ComplicationsFragment : Fragment() {

    private lateinit var cardioCirculatoryArrestSwitch: Switch
    private lateinit var deterioratingStateConsciousnessSwitch: Switch
    private lateinit var anisoMidriasiSwitch: Switch
    private lateinit var respiratoryFailureSwitch: Switch
    private lateinit var cardioCirculatoryShockSwitch: Switch
    private lateinit var landingInItinereSwitch: Switch
    private lateinit var deathInItinereButton: Button
    private lateinit var deathArrivalInPSButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_complications, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)

        cardioCirculatoryArrestSwitch.setOnClickListener {
            if (cardioCirculatoryArrestSwitch.isChecked)
                addHistoryEntry(cardioCirculatoryArrestSwitch.isChecked, this.getString(R.string.arresto_cardio_circolatorio))
        }
        deterioratingStateConsciousnessSwitch.setOnClickListener {
            if (deterioratingStateConsciousnessSwitch.isChecked)
                addHistoryEntry(deterioratingStateConsciousnessSwitch.isChecked, this.getString(R.string.deterioramento_stato_di_coscenza))
        }
        anisoMidriasiSwitch.setOnClickListener {
            if (anisoMidriasiSwitch.isChecked)
                addHistoryEntry(anisoMidriasiSwitch.isChecked, this.getString(R.string.anisocoria_midriasi))
        }
        respiratoryFailureSwitch.setOnClickListener {
            if (respiratoryFailureSwitch.isChecked)
                addHistoryEntry(respiratoryFailureSwitch.isChecked, this.getString(R.string.insufficienza_respiratoria))
        }
        cardioCirculatoryShockSwitch.setOnClickListener {
            if (cardioCirculatoryShockSwitch.isChecked)
                addHistoryEntry(cardioCirculatoryShockSwitch.isChecked, this.getString(R.string.shock_cardiocircolatorio))
        }
        landingInItinereSwitch.setOnClickListener {
            if (landingInItinereSwitch.isChecked)
                addHistoryEntry(landingInItinereSwitch.isChecked, this.getString(R.string.atterraggio_in_itinere_per_manovra_terapeutica))
        }
        deathInItinereButton.setOnClickListener {
            setButtonColor(deathInItinereButton, resources)
            deathInItinereButton.isEnabled = false
            deathArrivalInPSButton.isEnabled = false
            if (deathInItinereButton.isPressed)
                addHistoryEntry(deathInItinereButton.isPressed, "${this.getString(R.string.decesso)} ${this.getString(R.string.in_itinere)}")
        }
        deathArrivalInPSButton.setOnClickListener {
            setButtonColor(deathArrivalInPSButton, resources)
            deathArrivalInPSButton.isEnabled = false
            deathInItinereButton.isEnabled = false
            if (deathArrivalInPSButton.isPressed)
                addHistoryEntry(deathArrivalInPSButton.isPressed, "${this.getString(R.string.decesso)} ${this.getString(R.string.all_arrivo_in_ps)}")
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

    private fun getComponents(root: View) {
        root.apply {
            cardioCirculatoryArrestSwitch = findViewById(R.id.cardio_circulatory_arrest_switch)
            deterioratingStateConsciousnessSwitch = findViewById(R.id.deteriorating_state_consciousness_switch)
            anisoMidriasiSwitch = findViewById(R.id.aniso_midriasi_switch)
            respiratoryFailureSwitch = findViewById(R.id.respiratory_failure_switch)
            cardioCirculatoryShockSwitch = findViewById(R.id.cardiocirculatory_shock_switch)
            landingInItinereSwitch = findViewById(R.id.landing_in_itinere_switch)
            deathInItinereButton = findViewById(R.id.itinere_button)
            deathArrivalInPSButton = findViewById(R.id.arrival__in_ps_button)
        }
    }

    private fun setButtonColor(button: Button, resources: Resources) {
        if (!button.isActivated) {
            button.isActivated = true
            activateButton(button, resources)
        }
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
            deathInItinereButton.isActivated = true
            activateButton(deathInItinereButton, resources)
        }
        if (savedState.deathInPs) {
            deathArrivalInPSButton.isActivated = true
            activateButton(deathArrivalInPSButton, resources)
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
                deathInItinereButton.isActivated,
                deathArrivalInPSButton.isActivated)
    }
}
