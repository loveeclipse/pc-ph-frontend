package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
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
    private lateinit var noteEditText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_note, container, false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)
        noteEditText = root.findViewById(R.id.note_edit_text)
        val savedSet = sharedPreferences.getString("notes", null)
        if (savedSet != null) {
            noteEditText.text = savedSet
        } else {
        }
        val saveAndExitButton = root.findViewById<ImageButton>(R.id.note_image_button)
        saveAndExitButton.setOnClickListener {
                parentDialog.cancel()
        }
        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        sharedPreferences.edit().putString("notes", noteEditText.text.toString()).apply()
        super.onCancel(dialog)
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