package it.unibo.preh_frontend.dialog

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.utils.PermissionManager
import java.util.Locale

class NewPcCarItemsDialogFragment : DialogFragment() {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var placeEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar_items_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        placeEditText = root.findViewById(R.id.place_edit_text)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        updateLastAddress()

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.pccar_items_image_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.cancel()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCancel(dialog: DialogInterface) {
        // TODO save data, time and place in shared preferences
        super.onCancel(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }

    private fun updateLastAddress() {
        if (PermissionManager.checkPermission(requireActivity(), requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener(requireActivity()) {
                location: Location ->
                    location.apply {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        if (addresses.isNotEmpty()) {
                            placeEditText.setText(addresses[0].getAddressLine(0))
                        }
                    }
            }
        }
    }
}
