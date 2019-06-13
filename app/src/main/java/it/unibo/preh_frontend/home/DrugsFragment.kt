package it.unibo.preh_frontend.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.InputDialogFragment

class DrugsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_drugs, container, false)

        val insertCrystalloid =  root.findViewById<Button>(R.id.crystalloid_button)
        insertCrystalloid.setOnClickListener {
            InputDialogFragment().show(requireActivity().supportFragmentManager, "layout/input_dialog.xml")
        }
        // Inflate the layout for this fragment
        return root
    }


}
