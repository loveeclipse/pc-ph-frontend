package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.google.gson.Gson

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.utils.HistoryManager
import kotlinx.android.synthetic.main.fragment_maneuver.*

class ManeuverFragment : Fragment() {

    private lateinit var cervicalCollarSwitch: Switch
    private lateinit var immobilizationSwitch: Switch
    private lateinit var electricalCardioversionSwitch: Switch
    private lateinit var gastricProbeSwitch: Switch
    private lateinit var bladderProbeSwitch: Switch
    private lateinit var captureFrequencyEditText: EditText
    private lateinit var amperageEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_maneuver, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        cervicalCollarSwitch = root.findViewById(R.id.cervical_collar_switch)
        immobilizationSwitch = root.findViewById(R.id.immobilization_switch)
        electricalCardioversionSwitch = root.findViewById(R.id.electrical_cardioversion_switch)
        gastricProbeSwitch = root.findViewById(R.id.gastric_probe_switch)
        bladderProbeSwitch = root.findViewById(R.id.bladder_probe_switch)
        captureFrequencyEditText = root.findViewById(R.id.capture_frequency_edit_text)
        amperageEditText = root.findViewById(R.id.amperage_edit_text)
        root.findViewById<Button>(R.id.pacing_button).setOnClickListener {
        }
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

    override fun onDestroy() {
        val maneuverData = getData()
        HistoryManager.addEntry(maneuverData, sharedPreferences)
        super.onDestroy()
    }

    private fun applySharedPreferences(savedState: ManeuverData) {
        cervicalCollarSwitch.isChecked = savedState.cervicalCollar
        immobilizationSwitch.isChecked = savedState.immobilization
        electricalCardioversionSwitch.isChecked = savedState.electricalCardioversion
        gastricProbeSwitch.isChecked = savedState.gastricProbe
        bladderProbeSwitch.isChecked = savedState.bladderProbe

        captureFrequencyEditText.setText(savedState.captureFrequency)
        amperageEditText.setText(savedState.amperage)
    }

    fun getData(): ManeuverData {
        return ManeuverData(
                cervicalCollarSwitch.isChecked,
                immobilizationSwitch.isChecked,
                electricalCardioversionSwitch.isChecked,
                gastricProbeSwitch.isChecked,
                bladderProbeSwitch.isChecked,
                captureFrequencyEditText.text.toString(),
                amperageEditText.text.toString())
    }
}
