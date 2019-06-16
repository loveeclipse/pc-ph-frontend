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
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.utils.HistoryListAdapter
import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import it.unibo.preh_frontend.model.AnagraphicData
import it.unibo.preh_frontend.model.ComplicationsData
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.VitalParametersData


class HistoryDialogFragment : DialogFragment() {

    private var aList: ArrayList<HistoryData<PreHData>> = ArrayList()
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
        val historyType = object : TypeToken<ArrayList<HistoryData<PreHData>>>() {}.type

        val typeFactory = RuntimeTypeAdapterFactory
                .of(PreHData::class.java, "type") // Here you specify which is the parent class and what field particularizes the child class.
                .registerSubtype(AnagraphicData::class.java) // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
                .registerSubtype(ComplicationsData::class.java)
                .registerSubtype(ManeuverData::class.java)
                .registerSubtype(PatientStatusData::class.java)
                .registerSubtype(TreatmentData::class.java)
                .registerSubtype(VitalParametersData::class.java)


        val gson = GsonBuilder().registerTypeAdapterFactory(typeFactory).create()

        val newList = gson.fromJson<ArrayList<HistoryData<PreHData>>>(sharedPreferences.getString("historyList", null), historyType)
        if (newList != null) {
            aList.addAll(newList)
        } else {

        }


        val storiaList = root.findViewById(R.id.history_list) as ListView
        mAdapter = HistoryListAdapter(requireActivity(),aList)
        storiaList.adapter = mAdapter

        storiaList.setOnItemClickListener { parent, view, position, id ->
                val historyData = aList[position]

                Snackbar.make(view, historyData.event+"\n"+historyData.eventTime, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show()
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
