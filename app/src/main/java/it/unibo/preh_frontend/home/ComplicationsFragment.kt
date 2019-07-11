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
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton
import it.unibo.preh_frontend.utils.RetrofitClient
import it.unibo.preh_frontend.utils.DateManager

class ComplicationsFragment : Fragment() {

    private val ADD_OPTION: String = "Registrato"
    private val REMOVE_OPTION: String = "Annullato"

    private lateinit var cardioCirculatoryShockSwitch: Switch
    private lateinit var deterioratingStateConsciousnessSwitch: Switch
    private lateinit var anisoMidriasiSwitch: Switch
    private lateinit var respiratoryFailureSwitch: Switch
    private lateinit var landingInItinereSwitch: Switch
    private lateinit var deathInItinereButton: Button
    private lateinit var deathArrivalInPSButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    private var deathInItinere = false
    private var deathInPs = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_complications, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)

        cardioCirculatoryShockSwitch.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            if (cardioCirculatoryShockSwitch.isChecked) {
                setHistoryStatus(cardioCirculatoryShockSwitch.isChecked, this.getString(R.string.shock_cardiocircolatorio))
                RetrofitClient.postComplication("cardiocirculatory-shock", time)
            } else {
                RetrofitClient.deleteComplication("cardiocirculatory-shock")
            }
        }
        deterioratingStateConsciousnessSwitch.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            if (deterioratingStateConsciousnessSwitch.isChecked) {
                setHistoryStatus(deterioratingStateConsciousnessSwitch.isChecked, this.getString(R.string.deterioramento_stato_di_coscenza))
                RetrofitClient.postComplication("impaired-consciousness", time)
            } else {
            }
        }
        anisoMidriasiSwitch.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            if (anisoMidriasiSwitch.isChecked) {
                setHistoryStatus(anisoMidriasiSwitch.isChecked, this.getString(R.string.anisocoria_midriasi))
                RetrofitClient.postComplication("anisocoria-mydriasis", time)
            } else {
                RetrofitClient.deleteComplication("anisocoria-mydriasis")
            }
        }
        respiratoryFailureSwitch.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            if (respiratoryFailureSwitch.isChecked) {
                setHistoryStatus(respiratoryFailureSwitch.isChecked, this.getString(R.string.insufficienza_respiratoria))
                RetrofitClient.postComplication("respiratory-failure", time)
            } else {
                RetrofitClient.deleteComplication("respiratory-failure")
            }
        }
        landingInItinereSwitch.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            if (landingInItinereSwitch.isChecked) {
                setHistoryStatus(landingInItinereSwitch.isChecked, this.getString(R.string.atterraggio_in_itinere_per_manovra_terapeutica))
                RetrofitClient.postComplication("landing-in-itinere", time)
            } else {
                RetrofitClient.deleteComplication("landing-in-itinere")
            }
        }
        deathInItinereButton.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            setButtonColor(deathInItinereButton, resources,
                    "${this.getString(R.string.decesso)} ${this.getString(R.string.in_itinere)}")
            if (!deathInItinere) {
                deathInItinere = true
                RetrofitClient.postComplication("death-in-itinere", time)
            } else {
                deathInItinere = false
                RetrofitClient.deleteComplication("death-in-itinere")
            }
        }
        deathArrivalInPSButton.setOnClickListener {
            val time = DateManager.getStandardRepresentation()
            setButtonColor(deathArrivalInPSButton, resources,
                    "${this.getString(R.string.decesso)} ${this.getString(R.string.all_arrivo_in_ps)}")
            if (!deathInPs) {
                deathInPs = true
                RetrofitClient.postComplication("demise-in-ps", time)
            } else {
                deathInPs = false
                RetrofitClient.deleteComplication("demise-in-ps")
            }
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
            cardioCirculatoryShockSwitch = findViewById(R.id.cardiocirculatory_shock_switch)
            deterioratingStateConsciousnessSwitch = findViewById(R.id.deteriorating_state_consciousness_switch)
            anisoMidriasiSwitch = findViewById(R.id.aniso_midriasi_switch)
            respiratoryFailureSwitch = findViewById(R.id.respiratory_failure_switch)
            landingInItinereSwitch = findViewById(R.id.landing_in_itinere_switch)
            deathInItinereButton = findViewById(R.id.itinere_button)
            deathArrivalInPSButton = findViewById(R.id.arrival__in_ps_button)
        }
    }

    private fun setButtonColor(buttonPressed: Button, resources: Resources, complicationsName: String) {
        if (!buttonPressed.isActivated) {
            buttonPressed.isActivated = true
            activateButton(buttonPressed, resources)
            setHistoryStatus(buttonPressed.isActivated, complicationsName)
        } else {
            buttonPressed.isActivated = false
            deactivateButton(buttonPressed, resources)
            setHistoryStatus(buttonPressed.isActivated, complicationsName)
        }
    }

    private fun addHistoryEntry(complicationsValue: Boolean, option: String, complicationsName: String) {
        val complicationsData = ComplicationsHistoryData(
                complicationsValue,
                "$option $complicationsName"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ComplicationsHistoryData", Gson().toJson(complicationsData)).apply()
        HistoryManager.addEntry(complicationsData, sharedPreferences)
    }

    private fun setHistoryStatus(complicationsValue: Boolean, complicationsName: String) {
        when {
            complicationsValue ->
                addHistoryEntry(complicationsValue, ADD_OPTION, complicationsName)
            else ->
                addHistoryEntry(complicationsValue, REMOVE_OPTION, complicationsName)
        }
    }

    private fun applySharedPreferences(savedState: ComplicationsData) {
        cardioCirculatoryShockSwitch.isChecked = savedState.cardioCirculatoryShock
        deterioratingStateConsciousnessSwitch.isChecked = savedState.deterioratingStateConsciousness
        anisoMidriasiSwitch.isChecked = savedState.anisoMidriasi
        respiratoryFailureSwitch.isChecked = savedState.respiratoryFailure
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
                cardioCirculatoryShock = cardioCirculatoryShockSwitch.isChecked,
                deterioratingStateConsciousness = deterioratingStateConsciousnessSwitch.isChecked,
                anisoMidriasi = anisoMidriasiSwitch.isChecked,
                respiratoryFailure = respiratoryFailureSwitch.isChecked,
                landingInItinere = landingInItinereSwitch.isChecked,
                deathInItinere = deathInItinereButton.isActivated,
                deathInPs = deathArrivalInPSButton.isActivated)
    }
}
