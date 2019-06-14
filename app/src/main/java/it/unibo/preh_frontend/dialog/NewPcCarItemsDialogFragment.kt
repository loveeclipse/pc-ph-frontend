package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewPcCarItemsDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar_items_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.pccar_items_image_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }

        val time = root.findViewById<EditText>(R.id.time_editText)
        time.setText(SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time))

        val date = root.findViewById<EditText>(R.id.date_editText)
        date.setText(SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(Calendar.getInstance().time))

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