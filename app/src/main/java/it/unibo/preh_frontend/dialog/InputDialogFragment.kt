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
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.DrugsData

class InputDialogFragment : DialogFragment() {

    private var inputValue = 0
    private var unitOfMeasurement = ""
    private lateinit var inputValueEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveState: DrugsData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        dialog!!.setCanceledOnTouchOutside(false)

        inputValueEditText = root.findViewById(R.id.input_edit_text)
        inputValueEditText.setText(inputValue.toString())

        val unitUnitEditText = root.findViewById<TextView>(R.id.unit_of_measurement)
        unitUnitEditText.text = unitOfMeasurement

        val saveAndExitButton = root.findViewById<Button>(R.id.confirm_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }

        val cancelAndExitButton = root.findViewById<Button>(R.id.cancel_button)
        cancelAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }

        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        saveState = DrugsData(
                Integer.parseInt(inputValueEditText.text.toString()),
                Integer.parseInt(inputValueEditText.text.toString()),
                Integer.parseInt(inputValueEditText.text.toString()),
                Integer.parseInt(inputValueEditText.text.toString())
        )
        /*val gson = Gson()
        val stateAsJson = gson.toJson(saveState)
        sharedPreferences.edit().putString("drugsValues", stateAsJson).apply()
        val historyData: HistoryData<PreHData> = History("Modificati Farmaci", saveState, "14:00  15/06/2019")
        localHistoryList.add(historyData)
        val historyType = object : TypeToken<ArrayList<HistoryData<PreHData>>>() {
        }.type
        val historyListAsJson = gson.toJson(localHistoryList, historyType)
        sharedPreferences.edit().putString("historyList", historyListAsJson).apply()*/
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

    fun setInput(value: Int, unit: String) {
        inputValue = value
        unitOfMeasurement = unit
    }
}