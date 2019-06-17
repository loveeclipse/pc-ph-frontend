package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.reflect.TypeToken
import it.unibo.preh_frontend.model.HistoryData
import it.unibo.preh_frontend.utils.HistoryListAdapter
import com.google.gson.GsonBuilder
import it.unibo.preh_frontend.model.HistoryAnagraphicData
import it.unibo.preh_frontend.model.HistoryComplicationsData
import it.unibo.preh_frontend.model.HistoryManeuverData
import it.unibo.preh_frontend.model.HistoryPatientStatusData
import it.unibo.preh_frontend.model.HistoryTreatmentData
import it.unibo.preh_frontend.model.HistoryVitalParametersData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.utils.RuntimeTypeAdapterFactory

class HistoryDialogFragment : DialogFragment() {

    private var aList: ArrayList<HistoryData> = ArrayList()
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
        val historyType = object : TypeToken<ArrayList<HistoryData>>() {}.type

        val typeFactory = RuntimeTypeAdapterFactory
                .of(HistoryData::class.java, "type")
                .registerSubtype(HistoryAnagraphicData::class.java, "AnagraphicData")
                .registerSubtype(HistoryComplicationsData::class.java, "ComplicationsData")
                .registerSubtype(HistoryManeuverData::class.java, "ManeuverData")
                .registerSubtype(HistoryPatientStatusData::class.java, "PatientStatusData")
                .registerSubtype(HistoryTreatmentData::class.java, "TreatmentData")
                .registerSubtype(HistoryVitalParametersData::class.java, "VitalParametersData")

        val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create()

        aList = gson.fromJson(sharedPreferences.getString("historyList", null), historyType)
        Log.d("TEST",aList[0].eventData.toString())
        val storiaList = root.findViewById(R.id.history_list) as ListView
        mAdapter = HistoryListAdapter(requireActivity(), aList)
        storiaList.adapter = mAdapter

        storiaList.setOnItemClickListener { parent, view, position, id ->
            val historyData = aList[position]
            when (historyData.type){
                    "PatientStatusData" -> {
                        val data: PatientStatusData = historyData.eventData as PatientStatusData
                        Snackbar.make(view, data.traumaChiuso.toString() + "\n" + data.traumaPenetrante + "\n" + data.cascoCintura.toString() + "\n" +
                                data.emorragia.toString() + "\n" + data.vieAeree.toString() + "\n" + data.tachipnea.toString() + "\n" + data.voletCostale.toString() + "\n" +
                                data.ecofast.toString() + "\n" + data.bacinoStatus.toString() + "\n" + data.amputazione.toString() + "\n" + data.fratturaCranica.toString() + "\n" +
                                data.paraparesi.toString() + "\n" + data.tetraparesi.toString() + "\n" + data.parestesia.toString() + "\n" + data.criterioFisiologico.toString() + "\n" +
                                data.criterioDinamico.toString() + data.criterioAnatomico.toString() + "\n" + "\n" + data.shockIndex.toString() + "\n"
                        , Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show()}
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
