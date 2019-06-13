package it.unibo.preh_frontend.utils

import android.content.res.Resources
import android.os.Build
import android.widget.Button
import it.unibo.preh_frontend.R

object ButtonAppearance {
    fun activateButton(button: Button, resources: Resources) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.backgroundTintList = resources.getColorStateList(R.color.buttonSelectedBackgroundColor, null)
            button.setTextColor(resources.getColorStateList(R.color.buttonUnselectedBackgroundColor, null))
        } else {
            button.backgroundTintList = resources.getColorStateList(R.color.buttonSelectedBackgroundColor)
            button.setTextColor(resources.getColorStateList(R.color.buttonUnselectedBackgroundColor))
        }
    }

    fun deactivateButton(button: Button, resources: Resources) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.backgroundTintList = resources.getColorStateList(R.color.buttonUnselectedBackgroundColor, null)
            button.setTextColor(resources.getColorStateList(R.color.buttonSelectedBackgroundColor, null))
        } else {
            button.backgroundTintList = resources.getColorStateList(R.color.buttonUnselectedBackgroundColor)
            button.setTextColor(resources.getColorStateList(R.color.buttonSelectedBackgroundColor))
        }
    }
}