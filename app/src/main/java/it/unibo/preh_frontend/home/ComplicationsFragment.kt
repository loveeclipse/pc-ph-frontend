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

class ComplicationsFragment : Fragment() {

    private lateinit var arrestoCardioSwitch: Switch
    private lateinit var deterioramentoSwitch: Switch
    private lateinit var anisocoriaSwitch: Switch
    private lateinit var insuffRespiratoriaSwitch: Switch
    private lateinit var cardioShockSwitch: Switch
    private lateinit var atterraggioSwitch: Switch

    private lateinit var itinereButton: Button
    private lateinit var arrivoPsButton: Button

    private var itinereActive = false
    private var arrivoPsActive = false

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_complications, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        arrestoCardioSwitch = root.findViewById(R.id.arrest_switch)
        deterioramentoSwitch = root.findViewById(R.id.consciousness_deteriorarion_switch)
        anisocoriaSwitch = root.findViewById(R.id.aniso_midriasi_switch)
        insuffRespiratoriaSwitch = root.findViewById(R.id.insufResp_switch)
        cardioShockSwitch = root.findViewById(R.id.shockCard_switch)
        atterraggioSwitch = root.findViewById(R.id.atterraggio_switch)

        itinereButton = root.findViewById(R.id.itinere_button)
        arrivoPsButton = root.findViewById(R.id.arrivoPs_button)

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

    private fun applySharedPreferences(savedState: ComplicationsData) {
        arrestoCardioSwitch.isChecked = savedState.arrestoCardiocircolatorio
        deterioramentoSwitch.isChecked = savedState.deterioramento
        anisocoriaSwitch.isChecked = savedState.anisocoria
        insuffRespiratoriaSwitch.isChecked = savedState.insuffRespiratoria
        cardioShockSwitch.isChecked = savedState.cardioShock
        atterraggioSwitch.isChecked = savedState.atterraggio
        if (savedState.itinere) {
            // cambia colore a itinerebutton come attivo e disattiva arrivoPsButton
        }
        if (savedState.arrivoPs) {
            // cambia colore a arrivoPsButton come attivo e disattiva itinerebutton
        }
    }

    fun getData(): ComplicationsData {
        return ComplicationsData(
                arrestoCardioSwitch.isChecked,
                deterioramentoSwitch.isChecked,
                anisocoriaSwitch.isChecked,
                insuffRespiratoriaSwitch.isChecked,
                arrestoCardioSwitch.isChecked,
                atterraggioSwitch.isChecked,
                itinereActive,
                arrivoPsActive)
    }
}
