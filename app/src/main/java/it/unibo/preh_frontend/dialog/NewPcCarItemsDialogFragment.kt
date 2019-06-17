package it.unibo.preh_frontend.dialog

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
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
    private lateinit var locationText: TextView
    private lateinit var replaceButton: Button
    private val reqSetting = LocationRequest.create().apply {
        fastestInterval = 1000
        interval = 1000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 1.0f
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar_items_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        locationText = root.findViewById(R.id.location)
        replaceButton = root.findViewById(R.id.replace_button)

        updateLocation()

        replaceButton.setOnClickListener {
            root.findViewById<EditText>(R.id.place_edit_text).setText(locationText.text)
        }

        root.findViewById<ImageButton>(R.id.pccar_items_image_button).setOnClickListener {
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
                        context?.let {
                            val geocoder = Geocoder(it, Locale.getDefault())
                            try {
                                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                                if (addresses.isNotEmpty()) {
                                    requireActivity().runOnUiThread {
                                        locationText.text = addresses[0].getAddressLine(0)
                                        replaceButton.isEnabled = true
                                    }
                                    fusedLocationClient?.removeLocationUpdates(locationUpdates)
                                }
                            } catch (ex: IOException) {}
                        }
                    }
                }
            }
            fusedLocationClient?.requestLocationUpdates(reqSetting, locationUpdates, null)
        }
    }
}
