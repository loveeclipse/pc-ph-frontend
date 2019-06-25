package it.unibo.preh_frontend.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import it.unibo.preh_frontend.R

class NewPcCarReturnDialogFragment : NewPcCarItemsDialogFragment() {
    private lateinit var returnCode: Spinner
    private lateinit var hospital: Spinner
    private lateinit var vehicleRadiogroup: RadioGroup
    private lateinit var accompanyingMedic: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar_return_dialog, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        getComponents(root)
        initSpinner()
        createDialog()
        updateLocation()
        setListener()
        return root
    }

    private fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.returnCodeItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        returnCode.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.hospitalItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        hospital.adapter = newAdapter
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

    override fun setListener() {
        super.setListener()

        replaceButton.setOnClickListener {
            placeEditText.setText(locationText.text)
        }
        exitButton.setOnClickListener {
            if (placeEditText.text.toString() != "") {
                buttonToDisable.isEnabled = false
                buttonToEnable?.isEnabled = true
                dialog?.cancel()
            } else if (!exitDialog.isShowing)
                exitDialog.show()
        }
    }

    companion object {
        fun newInstance(historyName: String, buttonToDisable: Button, buttonToEnable: Button?) = NewPcCarReturnDialogFragment().apply {
            arguments = Bundle().apply {
                putString("historyName", historyName)
            }
            this.buttonToDisable = buttonToDisable
            this.buttonToEnable = buttonToEnable
        }
    }
}
