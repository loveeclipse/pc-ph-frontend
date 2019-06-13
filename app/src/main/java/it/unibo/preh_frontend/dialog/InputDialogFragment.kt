package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class InputDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.input_dialog, container, false)

        val saveAndExitButton2 = root.findViewById<ImageButton>(R.id.input_dialog_image_button)
        saveAndExitButton2.setOnClickListener {
            dialog!!.cancel()
        }

        val saveAndExitButton = root.findViewById<Button>(R.id.back_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(70 * width / 100, 40 * height / 100)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}