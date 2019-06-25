package it.unibo.preh_frontend.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.PhysiologicCriterionData

class PhysiologicCriterionDialog : DialogFragment() {
    private lateinit var GCSValueSwitch: Switch
    private lateinit var lowRespFrequencySwitch: Switch
    private lateinit var highRespFrequencySwitch: Switch
    private lateinit var ventilatorySupportSwitch: Switch
    private lateinit var lowBloodPressureSwitch: Switch
    private lateinit var hypertensionSwitch: Switch

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.physiologic_criterion_fragment, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        getComponents(root)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.physiologic_image_button)
        saveAndExitButton.setOnClickListener {
            dialog?.cancel()
        }

        setSharedPreferences()

        return root
    }

    private fun getComponents(root: View) {
        root.apply {
            GCSValueSwitch = findViewById(R.id.gcs_switch)
            lowRespFrequencySwitch = findViewById(R.id.low_respfreq_switch)
            highRespFrequencySwitch = findViewById(R.id.high_respfreq_switch)
            ventilatorySupportSwitch = findViewById(R.id.ventilatorysupp_switch)
            lowBloodPressureSwitch = findViewById(R.id.low_bloodpres_switch)
            hypertensionSwitch = findViewById(R.id.hypertension_switch)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        // SharedPreferences
        val criteria = PhysiologicCriterionData(GCSValueSwitch.isChecked, lowRespFrequencySwitch.isChecked, highRespFrequencySwitch.isChecked, ventilatorySupportSwitch.isChecked, lowBloodPressureSwitch.isChecked, hypertensionSwitch.isChecked)
        val gson = Gson()
        val criteriaAsJson = gson.toJson(criteria, PhysiologicCriterionData::class.java)
        sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
        super.onCancel(dialog)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8 * metrics.heightPixels / 10)
    }

    private fun setSharedPreferences() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
        if (savedState != null) {
            GCSValueSwitch.isChecked = savedState.gcsValue
            lowRespFrequencySwitch.isChecked = savedState.lowRespFreq
            highRespFrequencySwitch.isChecked = savedState.highRespFreq
            ventilatorySupportSwitch.isChecked = savedState.ventSupport
            lowBloodPressureSwitch.isChecked = savedState.lowBloodPress
            hypertensionSwitch.isChecked = savedState.hypertension
        }
    }
}