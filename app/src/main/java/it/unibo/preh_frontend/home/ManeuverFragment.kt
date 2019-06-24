package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.google.gson.Gson

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.dialog.PacingDialogFragment
import it.unibo.preh_frontend.model.ManeuverHistoryData

class ManeuverFragment : Fragment() {

    private lateinit var cervicalCollarSwitch: Switch
    private lateinit var immobilizationSwitch: Switch
    private lateinit var electricalCardioversionSwitch: Switch
    private lateinit var gastricProbeSwitch: Switch
    private lateinit var bladderProbeSwitch: Switch
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
        cervicalCollarSwitch.setOnClickListener {
            if (cervicalCollarSwitch.isChecked) history(cervicalCollarSwitch.isChecked, this.getString(R.string.collare_cervicale))
        }
        immobilizationSwitch = root.findViewById(R.id.immobilization_switch)
        immobilizationSwitch.setOnClickListener {
            if (immobilizationSwitch.isChecked) history(immobilizationSwitch.isChecked, this.getString(R.string.immobilizzazione))
        }
        electricalCardioversionSwitch = root.findViewById(R.id.electrical_cardioversion_switch)
        electricalCardioversionSwitch.setOnClickListener {
            if (electricalCardioversionSwitch.isChecked) history(electricalCardioversionSwitch.isChecked, this.getString(R.string.cardioversione_elettrica_sincronizzata))
        }
        gastricProbeSwitch = root.findViewById(R.id.gastric_probe_switch)
        gastricProbeSwitch.setOnClickListener {
            if (gastricProbeSwitch.isChecked) history(gastricProbeSwitch.isChecked, this.getString(R.string.sonda_gastrica))
        }
        bladderProbeSwitch = root.findViewById(R.id.bladder_probe_switch)
        bladderProbeSwitch.setOnClickListener {
            if (bladderProbeSwitch.isChecked) history(bladderProbeSwitch.isChecked, this.getString(R.string.sonda_vescicale))
        }
        root.findViewById<Button>(R.id.pacing_button).setOnClickListener {
            PacingDialogFragment().show(requireActivity().supportFragmentManager, "layout/fragment_pacing_dialog.xml")
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

    private fun history(maneuverValue: Boolean, maneuverName: String) {
        val maneuverData = ManeuverHistoryData(
                maneuverValue,
                "Applicata $maneuverName"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ManeuverHistoryData", Gson().toJson(maneuverData)).apply()
        HistoryManager.addEntry(maneuverData, sharedPreferences)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked
        return super.onOptionsItemSelected(item)
    }

    private fun applySharedPreferences(savedState: ManeuverData) {
        cervicalCollarSwitch.isChecked = savedState.cervicalCollar
        immobilizationSwitch.isChecked = savedState.immobilization
        electricalCardioversionSwitch.isChecked = savedState.electricalCardioversion
        gastricProbeSwitch.isChecked = savedState.gastricProbe
        bladderProbeSwitch.isChecked = savedState.bladderProbe
    }

    fun getData(): ManeuverData {
        return ManeuverData(
                cervicalCollarSwitch.isChecked,
                immobilizationSwitch.isChecked,
                electricalCardioversionSwitch.isChecked,
                gastricProbeSwitch.isChecked,
                bladderProbeSwitch.isChecked)
    }
}
