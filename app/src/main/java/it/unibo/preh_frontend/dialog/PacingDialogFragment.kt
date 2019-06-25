package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class PacingDialogFragment : DialogFragment() {

    private lateinit var captureFrequency: EditText
    private lateinit var amperage: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pacing_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        captureFrequency = root.findViewById(R.id.capture_frequency_edit_text)
        amperage = root.findViewById(R.id.amperage_edit_text)

        val exitDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Uscire senza salvare?")
            setMessage("Inserimento incompleto")
            setCancelable(true)
            setPositiveButton("Si") { d, _ ->
                d.cancel()
                dialog?.dismiss()
            }
            setNegativeButton("No") { d, _ -> d.cancel() }
        }.create()
        root.findViewById<ImageButton>(R.id.pacing_dialog_image_button).setOnClickListener {
            if (captureFrequency.text.toString() != "") {
                amperage.text.toString() != ""
                dialog?.cancel()
            } else if (!exitDialog.isShowing)
                    exitDialog.show()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}