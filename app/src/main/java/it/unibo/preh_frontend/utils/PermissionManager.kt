package it.unibo.preh_frontend.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionChecker {
    private fun checkPermission(context: Context, activity: Activity, vararg perm: String) {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            if (perm.toList().any {
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
                    }) {
                val dialog = AlertDialog.Builder(context)
                        .setTitle("Permission")
                        .setMessage("Permission needed!")
                        .setPositiveButton("OK") {
                            _, _ -> ActivityCompat.requestPermissions(activity, perm, 0)
                        }
                        .setNegativeButton("No") {
                            _, _ ->
                        }
                        .create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(activity, perm, 0)
            }
        }
    }
}