package it.unibo.preh_frontend.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.NewPcCarReturnData
import it.unibo.preh_frontend.utils.HistoryManager

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
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        returnCode.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.hospitalItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        hospital.apply {
            adapter = newAdapter
            setSelection(1)
        }
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
        vehicleRadiogroup.getChildAt(0).setOnClickListener { accompanyingMedic.isEnabled = true }
        vehicleRadiogroup.getChildAt(1).setOnClickListener { accompanyingMedic.isEnabled = false }
        exitButton.setOnClickListener {
            if (checkCompletion()) {
                buttonToDisable.isEnabled = false
                buttonToEnable?.isEnabled = true
                dialog?.cancel()
            } else if (!exitDialog.isShowing)
                exitDialog.show()
        }
    }

    private fun checkCompletion(): Boolean {
        return placeEditText.text.toString() != "" &&
                vehicleRadiogroup.checkedRadioButtonId != -1 &&
                returnCode.selectedItemPosition != 0
    }

    override fun onCancel(dialog: DialogInterface) {
        arguments?.getString("historyName")?.let {
            val newPcCarReturnData = NewPcCarReturnData(it,
                    placeEditText.text.toString(),
                    returnCode.selectedItemPosition,
                    hospital.selectedItemPosition,
                    vehicleRadiogroup.checkedRadioButtonId,
                    (!accompanyingMedic.isEnabled || accompanyingMedic.isChecked))
            val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(it, Gson().toJson(newPcCarReturnData)).apply()
            HistoryManager.addEntry(newPcCarReturnData, sharedPreferences)
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
