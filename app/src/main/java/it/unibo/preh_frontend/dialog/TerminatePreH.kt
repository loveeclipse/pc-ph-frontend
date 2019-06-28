package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.NewPcCarReturnData

class TerminatePreH : DialogFragment() {
    private lateinit var returnCodeSpinner: Spinner
    private lateinit var hospitalSpinner: Spinner
    private lateinit var hospitalPlaceSpinner: Spinner

    private var parentDialog: Dialog? = null
    private var mLastClickTime = SystemClock.elapsedRealtime()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.terminate_pre_h, container, false)
        parentDialog = dialog
        dialog?.setCanceledOnTouchOutside(false)
        getComponents(root)
        initSpinner()
        getPcCarData()
        root.findViewById<ImageButton>(R.id.terminate_items_image_button).setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime > 1000) {
                mLastClickTime = SystemClock.elapsedRealtime()
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setCancelable(true)
                    setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                        parentDialog?.dismiss()
                    }
                }
                builder.apply {
                    setTitle("Conferma Terminazione Pre-H?")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog?.cancel()
                        activity?.onBackPressed()
                    }
                }
                builder.create().show()
            }
        }

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 35*metrics.heightPixels / 100)
    }

    private fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.returnCodeItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        returnCodeSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.hospitalItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        hospitalSpinner.adapter = newAdapter

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.hospitalPlaceItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.dropdown_spinner_layout_dialog)
        hospitalPlaceSpinner.adapter = newAdapter
    }

    private fun getComponents(root: View) {
        root.apply {
            returnCodeSpinner = findViewById(R.id.return_code_spinner)
            hospitalSpinner = findViewById(R.id.hospital_spinner)
            hospitalPlaceSpinner = findViewById(R.id.hospital_place_spinner)
        }
    }

    private fun getPcCarData() {
        val newPcCarReturnData = Gson().fromJson(requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
                .getString(this.getString(R.string.partenza_dal_luogo_dell_incidente), null), NewPcCarReturnData::class.java)
        if (newPcCarReturnData != null) {
            returnCodeSpinner.setSelection(newPcCarReturnData.returnCode)
            hospitalSpinner.setSelection(newPcCarReturnData.hospital)
        }
    }
}