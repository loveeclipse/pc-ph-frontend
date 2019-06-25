package it.unibo.preh_frontend.dialog.history

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
import it.unibo.preh_frontend.model.DrugsData

class HistoryDrugsDialog : DialogFragment() {
    private lateinit var inputValueEditText: EditText
    private lateinit var unitEditText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)

        root.findViewById<Button>(R.id.confirm_button).setOnClickListener {
            dialog?.cancel()
        }

        root.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog?.dismiss()
        }

        getComponents(root)

        setData(arguments?.get("data") as DrugsData)

        return root
    }

    private fun getComponents(root: View) {
        root.apply {
            inputValueEditText = findViewById(R.id.input_edit_text)
            unitEditText = findViewById(R.id.unit_of_measurement)
        }
    }

    private fun setData(data: DrugsData) {
        println("data      --------------- $inputValueEditText ${data.drugsValue}")
        inputValueEditText.setText(data.drugsValue.toString())
        unitEditText.text = data.unitOfMeasure
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
        @JvmStatic
        fun newInstance(data: DrugsData) = HistoryDrugsDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }
}