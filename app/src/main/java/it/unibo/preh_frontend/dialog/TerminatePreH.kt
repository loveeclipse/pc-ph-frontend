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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.NewPcCarReturnData
import it.unibo.preh_frontend.model.dt_model.ReturnInformation
import it.unibo.preh_frontend.utils.CentralizationManager
import it.unibo.preh_frontend.utils.CurrentEventInfo
import it.unibo.preh_frontend.utils.DtIdentifiers
import it.unibo.preh_frontend.utils.RetrofitClient

class TerminatePreH : DialogFragment() {
    private lateinit var returnCodeSpinner: Spinner
    private lateinit var hospitalSpinner: Spinner
    private lateinit var hospitalPlaceSpinner: Spinner
    private lateinit var navController: NavController

    private var parentDialog: Dialog? = null
    private var mLastClickTime = SystemClock.elapsedRealtime()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.terminate_pre_h, container, false)
        parentDialog = dialog
        dialog?.setCanceledOnTouchOutside(false)
        getComponents(root)
        initSpinner()
        getPcCarData()
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
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
                        //sendReturnInformationToDt()
                        dialog.cancel()
                        parentDialog?.cancel()
                        backPressed()
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

    private fun backPressed() {
        setMissionOngoingState()
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        CentralizationManager.centralizationIsActive = false
        DtIdentifiers.clear()
        CurrentEventInfo.clear()
        navController.navigate(R.id.action_home_to_login)
    }

    private fun sendReturnInformationToDt() {
        if (hospitalPlaceSpinner.selectedItem.toString() == "Pronto soccorso") {
            RetrofitClient.sendReturnInformation(ReturnInformation(returnCodeSpinner.selectedItem.toString().toInt(), hospitalSpinner.selectedItem.toString(), hospitalPlaceSpinner.selectedItem.toString()))
        } else {
            RetrofitClient.sendReturnInformation(ReturnInformation(returnCodeSpinner.selectedItem.toString().toInt(), hospitalSpinner.selectedItem.toString(), null))
        }
    }

    private fun setMissionOngoingState() {
        RetrofitClient.putMissionOngoingState(false)
    }
}