package it.unibo.preh_frontend.dialog.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.NewPcCarReturnData
import it.unibo.preh_frontend.utils.DateManager

class HistoryNewPcCarReturnDialog : HistoryNewPcCarDialog() {
    private lateinit var returnCode: Spinner
    private lateinit var hospital: Spinner
    private lateinit var vehicleRadiogroup: RadioGroup
    private lateinit var accompanyingMedic: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_history_pccar_return_dialog, container, false)
        getComponents(root)
        vehicleRadiogroup.getChildAt(0).setOnClickListener { accompanyingMedic.isEnabled = true }
        vehicleRadiogroup.getChildAt(1).setOnClickListener { accompanyingMedic.isEnabled = false }
        initSpinner()
        setData(arguments?.get("data") as NewPcCarReturnData)
        val exitButton = root.findViewById<ImageButton>(R.id.pccar_items_image_button)
        exitButton.setOnClickListener {
            dialog?.cancel()
        }

        return root
    }

    override fun getComponents(root: View) {
        super.getComponents(root)
        root.apply {
            returnCode = findViewById(R.id.return_code)
            hospital = findViewById(R.id.hospital)
            vehicleRadiogroup = findViewById(R.id.vehicle_radiogroup)
            accompanyingMedic = findViewById(R.id.accompanying_medic)
        }
    }

    private fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.returnCodeItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        returnCode.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.hospitalItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        hospital.adapter = newAdapter
    }

    private fun setData(data: NewPcCarReturnData) {
        type.text = data.eventName
        place.setText(data.place)
        time.setText(DateManager.getHistoryRepresentation(data.eventTime))
        returnCode.setSelection(data.returnCode)
        hospital.setSelection(data.hospital)
        vehicleRadiogroup.check(data.vehicle)
        accompanyingMedic.isEnabled = (data.vehicle == vehicleRadiogroup.getChildAt(0).id)
        accompanyingMedic.isChecked = data.accompanyingMedic
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