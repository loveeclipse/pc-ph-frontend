package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import it.unibo.preh_frontend.utils.HistoryManager

class InputDialogFragment : DialogFragment() {

    private lateinit var inputValueEditText: EditText
    private lateinit var unitUnitEditText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        inputValueEditText = root.findViewById(R.id.input_edit_text)
        inputValueEditText.setText(arguments?.get(value).toString())

        unitUnitEditText = root.findViewById(R.id.unit_of_measurement)
        unitUnitEditText.text = arguments?.get(unitOfMeasurement).toString()

        root.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            dialog?.cancel()
        }

        root.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog?.dismiss()
        }

        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        val drugsData = DrugsData(Integer.parseInt(
                inputValueEditText.text.toString()),
                unitOfMeasurement,
                "Somministrazione ${arguments?.get(drugName)}"
        )
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("DrugsData", Gson().toJson(drugsData)).apply()
        HistoryManager.addEntry(drugsData, sharedPreferences)
        super.onCancel(dialog)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 35*metrics.heightPixels / 100)
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