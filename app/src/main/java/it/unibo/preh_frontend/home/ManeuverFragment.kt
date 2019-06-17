package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.google.gson.Gson

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData

class ManeuverFragment : Fragment() {

    private lateinit var collareCervicaleSwitch: Switch
    private lateinit var immobilizzazioneSwitch: Switch
    private lateinit var cardioversioneSwitch: Switch
    private lateinit var sondaGastricaSwitch: Switch
    private lateinit var sondaVescicaleSwitch: Switch
    private lateinit var frequenzaCatturaEditText: EditText
    private lateinit var amperaggioEditText: EditText

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_maneuver, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        collareCervicaleSwitch = root.findViewById(R.id.cervicale_switch)

        immobilizzazioneSwitch = root.findViewById(R.id.imm_switch)

        cardioversioneSwitch = root.findViewById(R.id.ces_switch)

        sondaGastricaSwitch = root.findViewById(R.id.gastrica_switch)

        sondaVescicaleSwitch = root.findViewById(R.id.vescicale_switch)

        frequenzaCatturaEditText = root.findViewById(R.id.freqcattura_edittext)

        amperaggioEditText = root.findViewById(R.id.amperaggio_edittext)

        return root
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("maneuversData", null), ManeuverData::class.java)
        if (savedState != null) {
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    private fun applySharedPreferences(savedState: ManeuverData) {
        collareCervicaleSwitch.isChecked = savedState.collare
        immobilizzazioneSwitch.isChecked = savedState.immobilizzazione
        cardioversioneSwitch.isChecked = savedState.cardioversione
        sondaGastricaSwitch.isChecked = savedState.sondaGastrica
        sondaVescicaleSwitch.isChecked = savedState.sondaVescicale

        frequenzaCatturaEditText.setText(savedState.freqCattura)
        amperaggioEditText.setText(savedState.amperaggio)
    }

    fun getData(): ManeuverData {
        return ManeuverData(
                collareCervicaleSwitch.isChecked,
                immobilizzazioneSwitch.isChecked,
                cardioversioneSwitch.isChecked,
                sondaGastricaSwitch.isChecked,
                sondaVescicaleSwitch.isChecked,
                frequenzaCatturaEditText.text.toString(),
                amperaggioEditText.text.toString(),
                time = "vole")
    }
}
