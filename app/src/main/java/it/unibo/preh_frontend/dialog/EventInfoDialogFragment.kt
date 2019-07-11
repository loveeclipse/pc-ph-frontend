package it.unibo.preh_frontend.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.utils.CurrentEventInfo

class EventInfoDialogFragment : Fragment() {
    private lateinit var dispatchCodeTextView: TextView
    private lateinit var dynamicTextView: TextView
    private lateinit var secondaryTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var involvedVehiclesTextView: TextView
    private lateinit var patientNumberTextView: TextView
    private lateinit var noteTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_event_info, container, false)

        dispatchCodeTextView = root.findViewById(R.id.dispatch_code_text)
        dynamicTextView = root.findViewById(R.id.dynamic_text)
        secondaryTextView = root.findViewById(R.id.secondary_text)
        addressTextView = root.findViewById(R.id.address_text)
        involvedVehiclesTextView = root.findViewById(R.id.involved_vehicles_text)
        patientNumberTextView = root.findViewById(R.id.patient_number_text)
        noteTextView = root.findViewById(R.id.note_edit_text)

        setEventInformation()

        return root
    }

    private fun setEventInformation() {
        dispatchCodeTextView.text = CurrentEventInfo.dispatchCode
        dynamicTextView.text = CurrentEventInfo.dynamic
        secondaryTextView.text = CurrentEventInfo.secondary.toString()
        addressTextView.text = CurrentEventInfo.address
        involvedVehiclesTextView.text = CurrentEventInfo.involvedVehicles.toString()
        patientNumberTextView.text = CurrentEventInfo.patientsNumber.toString()
        noteTextView.text = CurrentEventInfo.notes
    }
}
