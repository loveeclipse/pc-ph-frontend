package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.utils.HistoryManager

class NewPcCarDialogFragment : DialogFragment() {
    private lateinit var crewDepartureButton: Button
    private lateinit var arrivalOnSiteButton: Button
    private lateinit var departureFromSiteButton: Button
    private lateinit var landingHelipadButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar, container, false)
        dialog?.setCanceledOnTouchOutside(false)
        getComponents(root)
        val eventList = listOf(resources.getString(R.string.partenza_dell_equipaggio),
                resources.getString(R.string.arrivo_sul_luogo_dell_incidente),
                resources.getString(R.string.partenza_dal_luogo_dell_incidente),
                resources.getString(R.string.atterraggio_in_eliporto))
        checkEnabledButton(eventList)
        val buttons = listOf(crewDepartureButton,
                arrivalOnSiteButton,
                departureFromSiteButton,
                landingHelipadButton)

        for (i in 0..3) {
            if (i == 2)
                setButtonListener(true, eventList[i], buttons[i], buttons[i + 1])
            else
                setButtonListener(false, eventList[i], buttons[i], if (i < 3) buttons[i + 1] else null)
        }
        root.findViewById<ImageButton>(R.id.pcCar_image_button).setOnClickListener {
            dialog?.cancel()
        }

        return root
    }

    private fun setButtonListener(isReturning: Boolean, eventName: String, actualButton: Button, nextButton: Button?) {
        actualButton.setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("pcCar_items_dialog_fragment") == null)
                if (isReturning) {
                    NewPcCarReturnDialogFragment.newInstance(eventName, actualButton, nextButton)
                            .show(requireActivity().supportFragmentManager, "pcCar_items_dialog_fragment")
                } else {
                    NewPcCarItemsDialogFragment.newInstance(eventName, actualButton, nextButton)
                            .show(requireActivity().supportFragmentManager, "pcCar_items_dialog_fragment")
                }
        }
    }

    private fun getComponents(root: View) {
        root.apply {
            crewDepartureButton = findViewById(R.id.crew_departure_button)
            arrivalOnSiteButton = findViewById(R.id.arrival_on_site_button)
            departureFromSiteButton = findViewById(R.id.departure_from_site_button)
            landingHelipadButton = findViewById(R.id.landing_helipad_button)
        }
    }

    private fun checkEnabledButton(eventList: List<String>) {
        val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        val counter = HistoryManager.getEntryList(sharedPreferences).filter {
            data -> eventList.contains(data.eventName)
        }.size
        when (counter) {
            0 -> crewDepartureButton.isEnabled = true
            1 -> arrivalOnSiteButton.isEnabled = true
            2 -> departureFromSiteButton.isEnabled = true
            3 -> landingHelipadButton.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog?.window?.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}