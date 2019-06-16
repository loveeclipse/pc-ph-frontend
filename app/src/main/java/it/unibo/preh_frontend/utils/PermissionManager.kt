package it.unibo.preh_frontend.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {
    const val LOCATION_CODE = 42

    fun checkPermission(activity: Activity, context: Context, vararg perm: String): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            ActivityCompat.requestPermissions(activity, perm, LOCATION_CODE)
            return false
        }
        return true
    }
}