package it.unibo.preh_frontend.dialog


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R





class NewPcCarDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar, container, false)

        return root
    }
    override fun onResume() {
        super.onResume()
        //TODO !!PROTOTYPE!! FIND A BETTER IMPLEMENTATION (IF IT EXISTS)
        val displayMetrics = context!!.getResources().displayMetrics
        val dpHeight = (displayMetrics.heightPixels / displayMetrics.density.toInt())
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density.toInt()
        val window = dialog!!.window
        window!!.setLayout(dpWidth-100, dpHeight-200)
        window.setGravity(Gravity.CENTER)
    }
}