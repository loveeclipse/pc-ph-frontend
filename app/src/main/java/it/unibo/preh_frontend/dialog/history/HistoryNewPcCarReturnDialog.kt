package it.unibo.preh_frontend.dialog.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import it.unibo.preh_frontend.model.NewPcCarData
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.NewPcCarReturnData

class HistoryNewPcCarReturnDialog : HistoryNewPcCarDialog() {
    private lateinit var type: TextView
    private lateinit var place: EditText
    private lateinit var time: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history_pccar_dialog, container, false)

        getComponents(root)

        setData(arguments?.get("data") as NewPcCarData)

        val exitButton = root.findViewById<ImageButton>(R.id.pccar_items_image_button)
        exitButton.setOnClickListener {
            dialog?.cancel()
        }

        return root
    }

    private fun getComponents(root: View) {
        root.apply {
            type = findViewById(R.id.type)
            place = findViewById(R.id.place)
            time = findViewById(R.id.time)
        }
    }

    private fun setData(data: NewPcCarData) {
        type.text = data.eventName
        place.setText(data.place)
        time.setText(data.eventTime)
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    companion object {
        @JvmStatic
        fun newInstance(data: NewPcCarReturnData) = HistoryNewPcCarReturnDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }
}