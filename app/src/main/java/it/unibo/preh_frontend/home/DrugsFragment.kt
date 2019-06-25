package it.unibo.preh_frontend.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.InputDialogFragment
import it.unibo.preh_frontend.dialog.utils.DrugsDefaultValue.crystalloidValue
import it.unibo.preh_frontend.dialog.utils.DrugsDefaultValue.succinylcholineValue
import it.unibo.preh_frontend.dialog.utils.DrugsDefaultValue.fentanilValue
import it.unibo.preh_frontend.dialog.utils.DrugsDefaultValue.ketamineValue

class DrugsFragment : Fragment() {

    private val inputDialogTag = "input_dialog"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_drugs, container, false)

        root.findViewById<Button>(R.id.crystalloid_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag(inputDialogTag) == null)
                InputDialogFragment.newInstance(this.getString(R.string.cristalloidi), crystalloidValue, this.getString(R.string.milliliters))
                        .show(requireActivity().supportFragmentManager, inputDialogTag)
        }

        root.findViewById<Button>(R.id.succinylcholine_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag(inputDialogTag) == null)
                InputDialogFragment.newInstance(this.getString(R.string.succinilcolina), succinylcholineValue, this.getString(R.string.milligrams))
                        .show(requireActivity().supportFragmentManager, inputDialogTag)
        }

        root.findViewById<Button>(R.id.fentanil_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag(inputDialogTag) == null)
                InputDialogFragment.newInstance(this.getString(R.string.fentanil), fentanilValue, this.getString(R.string.micrograms))
                        .show(requireActivity().supportFragmentManager, inputDialogTag)
        }

        root.findViewById<Button>(R.id.ketamine_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag(inputDialogTag) == null)
                InputDialogFragment.newInstance(this.getString(R.string.ketamina), ketamineValue, this.getString(R.string.milligrams))
                        .show(requireActivity().supportFragmentManager, inputDialogTag)
        }

        // Inflate the layout for this fragment
        return root
    }
}
