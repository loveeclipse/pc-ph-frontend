package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class NoteDialogFragment : DialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog
    private lateinit var textualNoteView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_note, container, false)

        textualNoteView = root.findViewById<TextView>(R.id.editText3)

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.note_image_button)
        saveAndExitButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setMessage("Vuoi uscire senza salvare?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                    "Si"
            ) { dialog, id ->
                dialog.cancel()
                parentDialog.dismiss()
            }
            builder1.setNegativeButton(
                    "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }

        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        //SAVE NOTES TO SHAREDPREFERENCES

        sharedPreferences.edit().putString("historyList",textualNoteView.text.toString()).apply()
        dialog.cancel()
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10,height)
    }
}