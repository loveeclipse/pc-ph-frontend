package it.unibo.preh_frontend.dialog

import android.Manifest
import android.app.Dialog
import android.content.Context
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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.utils.PermissionManager
import it.unibo.preh_frontend.model.NewPcCarData
import it.unibo.preh_frontend.utils.HistoryManager
import java.io.IOException
import java.util.Locale

class NewPcCarItemsReturnFragment : NewPcCarItemsDialogFragment() {
}
