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
import it.unibo.preh_frontend.model.AnatomicCriterionData

class AnatomicCriterionDialog : DialogFragment() {
    private lateinit var traumaTorsoCrushSwitch: Switch
    private lateinit var penetratingWoundSwitch: Switch
    private lateinit var craniumFractureSwitch: Switch
    private lateinit var thoraxDeformitySwitch: Switch
    private lateinit var bodyBurnSwitch: Switch
    private lateinit var unstablePelvisSwitch: Switch
    private lateinit var vertebralLesionSwitch: Switch
    private lateinit var amputationSwitch: Switch

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.anatomic_criterion_fragment, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        getComponents(root)

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.anatomic_image_button)
        saveAndExitButton.setOnClickListener {
            dialog?.cancel()
        }

        setSharedPreferences()

        return root
    }

    private fun getComponents(root: View) {
        root.apply {
            traumaTorsoCrushSwitch = findViewById(R.id.trauma_tronco_switch)
            penetratingWoundSwitch = findViewById(R.id.ferita_penetramte_switch)
            craniumFractureSwitch = findViewById(R.id.frattura_cranica_switch)
            thoraxDeformitySwitch = findViewById(R.id.volet_costale_switch)
            bodyBurnSwitch = findViewById(R.id.ustione_switch)
            unstablePelvisSwitch = findViewById(R.id.bacino_instabile_switch)
            vertebralLesionSwitch = findViewById(R.id.lesione_vertebrale_switch)
            amputationSwitch = findViewById(R.id.amputazione_scuoiamento_switch)
        }
    }

    private fun setSharedPreferences() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("anatomicCriteria", null), AnatomicCriterionData::class.java)
        if (savedState != null) {
            traumaTorsoCrushSwitch.isChecked = savedState.traumaTorsoCrush
            penetratingWoundSwitch.isChecked = savedState.penetratingWound
            craniumFractureSwitch.isChecked = savedState.craniumFracture
            thoraxDeformitySwitch.isChecked = savedState.thoraxDeformity
            bodyBurnSwitch.isChecked = savedState.bodyBurn
            unstablePelvisSwitch.isChecked = savedState.unstablePelvis
            vertebralLesionSwitch.isChecked = savedState.vertebralLesion
            amputationSwitch.isChecked = savedState.amputation
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        val criteria = AnatomicCriterionData(traumaTorsoCrushSwitch.isChecked, penetratingWoundSwitch.isChecked, craniumFractureSwitch.isChecked, thoraxDeformitySwitch.isChecked, bodyBurnSwitch.isChecked, unstablePelvisSwitch.isChecked, vertebralLesionSwitch.isChecked, amputationSwitch.isChecked)
        val gson = Gson()
        val criteriaAsJson = gson.toJson(criteria, AnatomicCriterionData::class.java)
        sharedPreferences.edit().putString("anatomicCriteria", criteriaAsJson).apply()
        super.onCancel(dialog)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8 * metrics.heightPixels / 10)
    }
}