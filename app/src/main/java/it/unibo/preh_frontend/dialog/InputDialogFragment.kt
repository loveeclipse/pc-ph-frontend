package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class InputDialogFragment : DialogFragment() {

    private var inputValue = 0
    private var unitOfMeasurement = ""
    private lateinit var inputValueEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        inputValueEditText = root.findViewById(R.id.input_edit_text)
        inputValueEditText.setText(arguments?.get("inputValue").toString())

        val unitUnitEditText = root.findViewById<TextView>(R.id.unit_of_measurement)
        unitUnitEditText.text = arguments?.get("unitOfMeasurement").toString()

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
        fun newInstance(nameButtonPressed: String, inputValue: Int, unitOfMeasurement: String) =
                InputDialogFragment().apply {
            arguments = Bundle().apply {
                putString("nameButtonPressed", nameButtonPressed)
                putInt("inputValue", inputValue)
                putString("unitOfMeasurement", unitOfMeasurement)
            }
        }
    }
}