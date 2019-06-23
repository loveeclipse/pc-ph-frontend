package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.DrugsData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.utils.HistoryManager
import kotlin.collections.ArrayList

class InputDialogFragment : DialogFragment() {

    private lateinit var inputValueEditText: EditText
    private lateinit var unitUnitEditText: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var localHistoryList: ArrayList<PreHData>
    private lateinit var saveState: DrugsData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        dialog!!.setCanceledOnTouchOutside(false)

        inputValueEditText = root.findViewById(R.id.input_edit_text)
        inputValueEditText.setText(arguments?.get(value).toString())

        unitUnitEditText = root.findViewById(R.id.unit_of_measurement)
        unitUnitEditText.text = arguments?.get(unitOfMeasurement).toString()

        setSharedPreferences()

        root.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            dialog!!.cancel()
        }

        root.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog!!.cancel()
        }

        return root
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("drugs", null), DrugsData::class.java)
            println("status ------------------------ $newSaveState")
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    println("status ------------------------ ${newSaveState.drugsValue}")
                }
                saveState = newSaveState
            }
        }).start()
    }

    override fun onCancel(dialog: DialogInterface) {
        saveState = DrugsData(Integer.parseInt(
                inputValueEditText.text.toString()),
                "Somministrato farmaco ${arguments?.get(drugName)}"
        )
        val stateAsJson = Gson().toJson(saveState, DrugsData::class.java)
        sharedPreferences.edit().putString("drugs", stateAsJson).apply()

        HistoryManager.addVoice(saveState, sharedPreferences)
        super.onCancel(dialog)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 35*metrics.heightPixels / 100)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }

    companion object {
        private const val drugName = "nameButtonPressed"
        private const val value = "inputValue"
        private const val unitOfMeasurement = "unitOfMeasurement"
        fun newInstance(nameButtonPressed: String, inputValue: Int, unitOfMeasurementValue: String) =
                InputDialogFragment().apply {
            arguments = Bundle().apply {
                putString(drugName, nameButtonPressed)
                putInt(value, inputValue)
                putString(unitOfMeasurement, unitOfMeasurementValue)
            }
        }
    }
}