package it.unibo.preh_frontend.dialog.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.IppvData

open class HistoryIppvDialog : DialogFragment() {
    protected lateinit var vtEditText: EditText
    protected lateinit var frEditText: EditText
    protected lateinit var peepEditText: EditText
    protected lateinit var fio2EditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_ippv_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)

        getComponents(root)

        setData(arguments?.get("data") as IppvData)

        val saveAndExit = root.findViewById<ImageButton>(R.id.ippv_dialog_image_button)
        saveAndExit.setOnClickListener {
            dialog?.cancel()
        }

        return root
    }


    open fun getComponents(root: View){
        root.apply {
            vtEditText = findViewById(R.id.vt_edit_text)
            frEditText = findViewById(R.id.fr_edit_text)
            peepEditText = findViewById(R.id.peep_edit_text)
            fio2EditText = findViewById(R.id.fiO2_edit_text)
        }
    }

    protected open fun setData(data: IppvData) {
        vtEditText.setText(data.vt)
        frEditText.setText(data.fr)
        peepEditText.setText(data.peep)
        fio2EditText.setText(data.fio2)
    }


    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    companion object {

        @JvmStatic
        fun newInstance(data: IppvData) = HistoryIppvDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }
}