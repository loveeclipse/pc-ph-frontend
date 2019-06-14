package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class InputDialogFragment : DialogFragment() {

    private var inputValue = 0
    private var unitOfMeasurement = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        val saveAndExitImage = root.findViewById<ImageButton>(R.id.input_dialog_image_button)
        saveAndExitImage.setOnClickListener {
            dialog!!.cancel()
        }

        val saveAndExitButton = root.findViewById<Button>(R.id.cancel_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }

        val inputValueEditText = root.findViewById<EditText>(R.id.input_edit_text)
        inputValueEditText.setText(inputValue.toString())

        val unitUnitEditText = root.findViewById<TextView>(R.id.unit_of_measurement)
        unitUnitEditText.text = unitOfMeasurement

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

    fun setInput(value: Int, unit: String) {
        inputValue = value
        unitOfMeasurement = unit
    }
}