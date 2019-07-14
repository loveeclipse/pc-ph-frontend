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
import it.unibo.preh_frontend.utils.RetrofitClient
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.dialog.PacingDialogFragment
import it.unibo.preh_frontend.model.ManeuverHistoryData
import it.unibo.preh_frontend.utils.DateManager

class ManeuverFragment : Fragment() {

    private val ADD_OPTION: String = "Applicazione"
    private val REMOVE_OPTION: String = "Annullato"

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

        getComponents(root)

        cervicalCollarSwitch.setOnClickListener {
            setHistoryStatus(cervicalCollarSwitch.isChecked, this.getString(R.string.collare_cervicale))
            if (cervicalCollarSwitch.isChecked) {
                val time: String = DateManager.getStandardRepresentation()
                RetrofitClient.postSimpleManeuver("cervical-collar", time)
            } else {
                RetrofitClient.deleteSimpleManeuver("cervical-collar")
            }
        }
        immobilizationSwitch.setOnClickListener {
            setHistoryStatus(immobilizationSwitch.isChecked, this.getString(R.string.immobilizzazione))
            if (immobilizationSwitch.isChecked) {
                val time: String = DateManager.getStandardRepresentation()
                RetrofitClient.postSimpleManeuver("immobilization", time)
            } else {
                RetrofitClient.deleteSimpleManeuver("immobilization")
            }
        }
        electricalCardioversionSwitch.setOnClickListener {
            setHistoryStatus(electricalCardioversionSwitch.isChecked, this.getString(R.string.cardioversione_elettrica_sincronizzata))
            if (electricalCardioversionSwitch.isChecked) {
                val time: String = DateManager.getStandardRepresentation()
                RetrofitClient.postSimpleManeuver("electrical-cardioversion", time)
            } else {
                RetrofitClient.deleteSimpleManeuver("electrical-cardioversion")
            }
        }
        gastricProbeSwitch.setOnClickListener {
            setHistoryStatus(gastricProbeSwitch.isChecked, this.getString(R.string.sonda_gastrica))
            if (gastricProbeSwitch.isChecked) {
                val time: String = DateManager.getStandardRepresentation()
                RetrofitClient.postSimpleManeuver("gastric-probe", time)
            } else {
                RetrofitClient.deleteSimpleManeuver("gastric-probe")
            }
        }
        bladderProbeSwitch.setOnClickListener {
            setHistoryStatus(bladderProbeSwitch.isChecked, this.getString(R.string.sonda_vescicale))
            if (bladderProbeSwitch.isChecked) {
                val time: String = DateManager.getStandardRepresentation()
                RetrofitClient.postSimpleManeuver("bladder-catheter", time)
            } else {
                RetrofitClient.deleteSimpleManeuver("bladder-catheter")
            }
        }
        root.findViewById<Button>(R.id.pacing_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("fragment_pacing_dialog") == null)
                PacingDialogFragment().show(requireActivity().supportFragmentManager, "fragment_pacing_dialog")
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

    private fun addHistoryEntry(maneuverValue: Boolean, option: String, maneuverName: String) {
        val maneuverData = ManeuverHistoryData(
                maneuverValue,
                "$option $maneuverName"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ManeuverHistoryData", Gson().toJson(maneuverData)).apply()
        HistoryManager.addEntry(maneuverData, sharedPreferences)
    }

    private fun setHistoryStatus(maneuverValue: Boolean, maneuverName: String) {
        when {
            maneuverValue ->
                addHistoryEntry(maneuverValue, ADD_OPTION, maneuverName)
            else ->
                addHistoryEntry(maneuverValue, REMOVE_OPTION, maneuverName)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked
        return super.onOptionsItemSelected(item)
    }

    private fun getComponents(root: View) {
        root.apply {
            cervicalCollarSwitch = findViewById(R.id.cervical_collar_switch)
            immobilizationSwitch = findViewById(R.id.immobilization_switch)
            electricalCardioversionSwitch = findViewById(R.id.electrical_cardioversion_switch)
            gastricProbeSwitch = findViewById(R.id.gastric_probe_switch)
            bladderProbeSwitch = findViewById(R.id.bladder_probe_switch)
        }
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
