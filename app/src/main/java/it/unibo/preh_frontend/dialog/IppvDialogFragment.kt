package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryIppvDialog
import it.unibo.preh_frontend.model.IppvData
import it.unibo.preh_frontend.model.dt_model.IppvTreatment
import it.unibo.preh_frontend.utils.HistoryManager
import it.unibo.preh_frontend.utils.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

class IppvDialogFragment : HistoryIppvDialog() {
    private lateinit var parentDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_ippv_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        parentDialog = dialog!!
        getComponents(root)

        val exitButton = root.findViewById<ImageButton>(R.id.ippv_dialog_image_button)
        exitButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.apply {
                setCancelable(true)
                setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            }
            if (checkEveryField()) {
                builder.apply {
                    setTitle("Conferma Valori IPPV")
                    setMessage("I dati inseriti saranno salvati")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog.cancel()
                    }
                }
            } else {
                builder.apply {
                    setTitle("Uscire senza salvare?")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog.dismiss()
                    }
                }
            }
            val exitDialog = builder.create()
            exitDialog.show()
        }

        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        addHistoryEntry(vtEditText.text.toString(), frEditText.text.toString(), peepEditText.text.toString(), fio2EditText.text.toString())
        sendIppvDataToDt()
        super.onCancel(dialog)
    }

    private fun sendIppvDataToDt() {
        val time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().time)
        val ippvTreatment = IppvTreatment(vtEditText.text.toString().toInt(),
                frEditText.text.toString().toInt(),
                peepEditText.text.toString().toInt(),
                fio2EditText.text.toString().toInt(),
                time)
        RetrofitClient.postIppvTreatment(ippvTreatment)
    }

    private fun addHistoryEntry(vt: String, fr: String, peep: String, fio2: String) {
        val ippvData = IppvData(vt, fr, peep, fio2, "Eseguito IPPV")

        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ComplicationsHistoryData", Gson().toJson(ippvData)).apply()
        HistoryManager.addEntry(ippvData, sharedPreferences)
    }

    private fun checkEveryField(): Boolean {
        return (vtEditText.text.toString() != "" &&
                frEditText.text.toString() != "" &&
                peepEditText.text.toString() != "" &&
                fio2EditText.text.toString() != "")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}