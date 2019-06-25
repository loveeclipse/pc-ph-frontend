package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.PhysiologicCriterionData
import it.unibo.preh_frontend.dialog.IppvDialogFragment
import it.unibo.preh_frontend.utils.CentralizationManager
import it.unibo.preh_frontend.utils.ButtonAppearance.activateButton
import it.unibo.preh_frontend.utils.ButtonAppearance.deactivateButton

class TreatmentFragment : Fragment() {

    private lateinit var sublussazioneButton: Button
    private lateinit var guedelButton: Button
    private lateinit var cricoTirotomiaButton: Button
    private lateinit var tuboTrachealeButton: Button
    private lateinit var minitoracotomiaSxButton: Button
    private lateinit var minitoracotomiaDxButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    private var subulussazioneIsActive = false
    private var guedelIsActive = false
    private var cricoTirotomiaIsActive = false
    private var tuboTrachealeIsActive = false
    private var minitoracotomiaSxIsActive = false
    private var minitoracotomiaDxIsActive = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_treatment, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        val peripheralSpinner = root.findViewById<Spinner>(R.id.periferica_spinner)
        val centralSpinner = root.findViewById<Spinner>(R.id.central_spinner)
        val intraosseousSpinner = root.findViewById<Spinner>(R.id.intraosseous_spinner)

        var adapter = ArrayAdapter.createFromResource(requireContext(), R.array.gaugeSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        peripheralSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.frenchSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        centralSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.sizeSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        intraosseousSpinner.adapter = adapter

        root.findViewById<Button>(R.id.ippv_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("fragment_ippv_dialog") == null)
                IppvDialogFragment().show(requireActivity().supportFragmentManager, "fragment_ippv_dialog")
        }

        sublussazioneButton = root.findViewById(R.id.sublussazione_button)
        guedelButton = root.findViewById(R.id.guedel_button)
        cricoTirotomiaButton = root.findViewById(R.id.tirotomia_button)
        tuboTrachealeButton = root.findViewById(R.id.tubotrach_button)
        tuboTrachealeButton.setOnClickListener {
            if (!tuboTrachealeIsActive) {
                tuboTrachealeIsActive = true
                val gson = Gson()
                var physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
                if (physiologicCriteria == null) {
                    physiologicCriteria = PhysiologicCriterionData()
                }
                physiologicCriteria.highRespFreq = true
                val criteriaAsJson = gson.toJson(physiologicCriteria)
                sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
                if (CentralizationManager.determineCentralization(requireContext())) {
                    requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
                } else {
                    requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
                }
                activateButton(tuboTrachealeButton, resources)
            } else {
                tuboTrachealeIsActive = false
                val gson = Gson()
                var physiologicCriteria = gson.fromJson(sharedPreferences.getString("physiologicCriteria", null), PhysiologicCriterionData::class.java)
                if (physiologicCriteria == null) {
                    physiologicCriteria = PhysiologicCriterionData()
                }
                physiologicCriteria.highRespFreq = false
                val criteriaAsJson = gson.toJson(physiologicCriteria)
                sharedPreferences.edit().putString("physiologicCriteria", criteriaAsJson).apply()
                if (CentralizationManager.determineCentralization(requireContext())) {
                    requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
                } else {
                    requireActivity().findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
                }
                deactivateButton(tuboTrachealeButton, resources)
            }
        }
        minitoracotomiaSxButton = root.findViewById(R.id.minitoracotomiaSx_button)
        minitoracotomiaDxButton = root.findViewById(R.id.minitoracotomiaDx_button)

        return root
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("treatmentData", null), TreatmentData::class.java)
        if (savedState != null) {
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    private fun applySharedPreferences(savedState: TreatmentData) {
        // setta i bottoni a seconda del valore in savestate
        if (savedState.sublussazione) {
            sublussazioneButton.backgroundTintList = resources.getColorStateList(R.color.colorAccent)
        }
    }

    fun getData(): TreatmentData {
        return TreatmentData(
                subulussazioneIsActive,
                guedelIsActive,
                cricoTirotomiaIsActive,
                tuboTrachealeIsActive,
                minitoracotomiaSxIsActive,
                minitoracotomiaDxIsActive)
    }
}
