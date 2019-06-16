package it.unibo.preh_frontend.dialog

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.utils.PermissionManager
import java.io.IOException
import java.util.Locale

class NewPcCarItemsDialogFragment : DialogFragment() {
    private lateinit var placeEditText: EditText
    private val reqSetting = LocationRequest.create().apply {
        fastestInterval = 1000
        interval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 1.0f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar_items_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        placeEditText = root.findViewById(R.id.place_edit_text)

        updateLocation()

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

    private fun updateLocation() {
        if (PermissionManager.checkPermission(requireActivity(), requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            val locationUpdates = object : LocationCallback() {
                override fun onLocationResult(localResult: LocationResult) {
                    val locationUpdates = this
                    localResult.locations.last().apply {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        try {
                            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                            if (addresses.isNotEmpty()) {
                                requireActivity().runOnUiThread {
                                    placeEditText.setText(addresses[0].getAddressLine(0))
                                }
                                fusedLocationClient?.removeLocationUpdates(locationUpdates)
                            }
                        } catch (ex: IOException) {}
                    }
                }
            }
            fusedLocationClient?.requestLocationUpdates(reqSetting, locationUpdates, null)
        }
    }
}
