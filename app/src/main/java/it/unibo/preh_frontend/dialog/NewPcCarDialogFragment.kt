package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R

class NewPcCarDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pccar, container, false)
        dialog!!.setCanceledOnTouchOutside(false)

        root.findViewById<Button>(R.id.crew_departure_button).setOnClickListener {
            NewPcCarItemsDialogFragment.newInstance(resources.getString(R.string.partenza_dell_equipaggio))
                    .show(requireActivity().supportFragmentManager, "layout/fragment_pccar_items_dialog.xml")
        }

        root.findViewById<Button>(R.id.arrival_on_site_button).setOnClickListener {
            NewPcCarItemsDialogFragment.newInstance(resources.getString(R.string.arrivo_sul_luogo_dell_incidente))
                    .show(requireActivity().supportFragmentManager, "layout/fragment_pccar_items_dialog.xml")
        }

        root.findViewById<Button>(R.id.departure_from_site_button).setOnClickListener {
            NewPcCarItemsDialogFragment.newInstance(resources.getString(R.string.partenza_dal_luogo_dell_incidente))
                    .show(requireActivity().supportFragmentManager, "layout/fragment_pccar_items_dialog.xml")
        }

        root.findViewById<Button>(R.id.landing_helipad_button).setOnClickListener {
            NewPcCarItemsDialogFragment.newInstance(resources.getString(R.string.atterraggio_in_eliporto))
                    .show(requireActivity().supportFragmentManager, "layout/fragment_pccar_items_dialog.xml")
        }

        root.findViewById<ImageButton>(R.id.pcCar_image_button).setOnClickListener {
            dialog!!.cancel()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        dialog!!.window!!.setLayout(metrics.widthPixels, 8*metrics.heightPixels / 10)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity!!, theme) {
            override fun onBackPressed() {
            }
        }
    }
}