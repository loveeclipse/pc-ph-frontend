package it.unibo.preh_frontend.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class AnatomicCriterionDialog : DialogFragment() {
    private lateinit var GCSValueSwitch: Switch
    private lateinit var lowRespFrequencySwitch: Switch
    private lateinit var highRespFrequencySwitch: Switch
    private lateinit var ventilatorySupportSwitch: Switch
    private lateinit var lowBloodPressureSwitch: Switch
    private lateinit var hypertensionSwitch: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.anatomic_criterion_fragment, container, false)

        GCSValueSwitch = root.findViewById(R.id.gcs_switch)
        lowRespFrequencySwitch = root.findViewById(R.id.low_respfreq_switch)
        highRespFrequencySwitch = root.findViewById(R.id.high_respfreq_switch)
        ventilatorySupportSwitch = root.findViewById(R.id.ventilatorysupp_switch)
        lowBloodPressureSwitch = root.findViewById(R.id.low_bloodpres_switch)
        hypertensionSwitch = root.findViewById(R.id.hypertension_switch)


        val saveAndExitButton = root.findViewById<ImageButton>(R.id.anatomic_image_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }


        return root
    }
}