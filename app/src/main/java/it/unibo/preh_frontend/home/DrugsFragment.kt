package it.unibo.preh_frontend.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.InputDialogFragment

class DrugsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_drugs, container, false)

        val dialog = InputDialogFragment()
        val insertCrystalloid = root.findViewById<Button>(R.id.crystalloid_button)
        insertCrystalloid.setOnClickListener {
            dialog.show(requireActivity().supportFragmentManager, "layout/input_dialog.xml")
        }
        dialog.setInput(500, "ml")

        val insertSuccinylcholine = root.findViewById<Button>(R.id.succinylcholine_button)
        insertSuccinylcholine.setOnClickListener {
            InputDialogFragment().show(requireActivity().supportFragmentManager, "layout/input_dialog.xml")
        }

        val insertFentanil = root.findViewById<Button>(R.id.fentanil_button)
        insertFentanil.setOnClickListener {
            InputDialogFragment().show(requireActivity().supportFragmentManager, "layout/input_dialog.xml")
        }

        val insertKetamine = root.findViewById<Button>(R.id.ketamine_button)
        insertKetamine.setOnClickListener {
            InputDialogFragment().show(requireActivity().supportFragmentManager, "layout/input_dialog.xml")
        }
        // Inflate the layout for this fragment
        return root
    }
}
