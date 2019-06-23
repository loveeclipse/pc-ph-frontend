package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import android.widget.ListView
import com.google.gson.reflect.TypeToken
import it.unibo.preh_frontend.utils.HistoryListAdapter
import com.google.gson.GsonBuilder
import it.unibo.preh_frontend.dialog.history.HistoryPatientStatusDialog
import it.unibo.preh_frontend.dialog.history.HistoryVitalParametersDialog
import it.unibo.preh_frontend.model.AnagraphicData
import it.unibo.preh_frontend.model.ComplicationsData
import it.unibo.preh_frontend.model.DrugsData
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.utils.RuntimeTypeAdapterFactory

class HistoryDialogFragment : DialogFragment() {

    private var aList: ArrayList<PreHData> = ArrayList()
    private lateinit var mAdapter: HistoryListAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        val historyType = object : TypeToken<ArrayList<PreHData>>() {}.type

        val typeFactory = RuntimeTypeAdapterFactory
                .of(PreHData::class.java, "type")
                .registerSubtype(AnagraphicData::class.java, "AnagraphicData")
                .registerSubtype(ComplicationsData::class.java, "ComplicationsData")
                .registerSubtype(ManeuverData::class.java, "ManeuverData")
                .registerSubtype(PatientStatusData::class.java, "PatientStatusData")
                .registerSubtype(TreatmentData::class.java, "TreatmentData")
                .registerSubtype(VitalParametersData::class.java, "VitalParametersData")
                .registerSubtype(DrugsData::class.java, "DrugsData")

        val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create()

        aList = gson.fromJson(sharedPreferences.getString("historyList", null), historyType)
        val storiaList = root.findViewById(R.id.history_list) as ListView
        mAdapter = HistoryListAdapter(requireActivity(), aList)
        storiaList.adapter = mAdapter

        storiaList.setOnItemClickListener { _, _, position, _ ->
            val historyData = aList[position]
            when (historyData.type) {
                "VitalParametersData" -> {
                    val fragment = HistoryVitalParametersDialog.newInstance(historyData as VitalParametersData)
                    fragment.show(requireActivity().supportFragmentManager, "history_vital_parameters_fragment")
                }
                "PatientStatusData" -> {
                    val fragment = HistoryPatientStatusDialog.newInstance(historyData as PatientStatusData)
                    fragment.show(requireActivity().supportFragmentManager, "history_patient_status_fragment")
                }
            }
        }

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.history_image_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.dismiss()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}
