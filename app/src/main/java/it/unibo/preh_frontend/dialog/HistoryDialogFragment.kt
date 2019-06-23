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
import it.unibo.preh_frontend.dialog.history.HistoryNewPcCarDialog
import it.unibo.preh_frontend.utils.HistoryListAdapter
import it.unibo.preh_frontend.dialog.history.HistoryPatientStatusDialog
import it.unibo.preh_frontend.dialog.history.HistoryVitalParametersDialog
import it.unibo.preh_frontend.model.NewPcCarData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.utils.HistoryManager

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

        aList = HistoryManager.getEntryList(sharedPreferences)
        val historyList = root.findViewById(R.id.history_list) as ListView
        mAdapter = HistoryListAdapter(requireActivity(), aList)
        historyList.adapter = mAdapter

        historyList.setOnItemClickListener { _, _, position, _ ->
            val historyData = aList[position]
            when (historyData.type) {
                "VitalParametersData" -> {
                    HistoryVitalParametersDialog.newInstance(historyData as VitalParametersData)
                            .show(requireActivity().supportFragmentManager, "history_vital_parameters_fragment")
                }
                "PatientStatusData" -> {
                    HistoryPatientStatusDialog.newInstance(historyData as PatientStatusData)
                            .show(requireActivity().supportFragmentManager, "history_patient_status_fragment")
                }
                "NewPcCarData" -> {
                    HistoryNewPcCarDialog.newInstance(historyData as NewPcCarData)
                            .show(requireActivity().supportFragmentManager, "history_newpccar_fragment")
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
