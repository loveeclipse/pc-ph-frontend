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
import it.unibo.preh_frontend.dialog.utils.DrugsValue.crystalloidValue
import it.unibo.preh_frontend.dialog.utils.DrugsValue.succinylcholineValue
import it.unibo.preh_frontend.dialog.utils.DrugsValue.fentanilValue
import it.unibo.preh_frontend.dialog.utils.DrugsValue.ketamineValue

class DrugsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private val inputDialogLayout = "layout/input_dialog.xml"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_drugs, container, false)

        val insertCrystalloid = root.findViewById<Button>(R.id.crystalloid_button)
        insertCrystalloid.setOnClickListener {
            val dialog = InputDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, inputDialogLayout)
            dialog.setInput(crystalloidValue, this.getString(R.string.milliliters))
        }

        val insertSuccinylcholine = root.findViewById<Button>(R.id.succinylcholine_button)
        insertSuccinylcholine.setOnClickListener {
            val dialog = InputDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, inputDialogLayout)
            dialog.setInput(succinylcholineValue, this.getString(R.string.milligrams))
        }

        val insertFentanil = root.findViewById<Button>(R.id.fentanil_button)
        insertFentanil.setOnClickListener {
            val dialog = InputDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, inputDialogLayout)
            dialog.setInput(fentanilValue, this.getString(R.string.micrograms))
        }

        val insertKetamine = root.findViewById<Button>(R.id.ketamine_button)
        insertKetamine.setOnClickListener {
            val dialog = InputDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, inputDialogLayout)
            dialog.setInput(ketamineValue, this.getString(R.string.milligrams))
        }

        // Inflate the layout for this fragment
        return root
    }
}
