package it.unibo.preh_frontend.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R

class TreatmentFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_treatment, container, false)
        val peripheralSpinner = root.findViewById<Spinner>(R.id.periferica_spinner)
        val centralSpinner = root.findViewById<Spinner>(R.id.central_spinner)
        val intraosseousSpinner = root.findViewById<Spinner>(R.id.intraosseous_spinner)

        var adapter = ArrayAdapter.createFromResource(requireContext(), R.array.gaugeSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        peripheralSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.frenchSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        centralSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.sizeSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        intraosseousSpinner.adapter = adapter

       /* root.findViewById<Button>(R.id.periferica_button).setOnClickListener {
            setPeripheral(peripheralSpinner.selectedItem.toString())
        }
        root.findViewById<Button>(R.id.central_button).setOnClickListener {
            setCentral(centralSpinner.selectedItem.toString())
        }
        root.findViewById<Button>(R.id.intraosseous_button).setOnClickListener {
            setIntraosseous(intraosseousSpinner.selectedItem.toString())
        }*/
        return root
    }

    /*private fun setPeripheral(peripheral: String){
        requireActivity().apply {
            findViewById<TextView>(R.id.periferica_spinner).text = peripheral
        }
    }
    private fun setCentral(central: String){
        requireActivity().apply {
            findViewById<TextView>(R.id.central_spinner).text = central
        }
    }

    private fun setIntraosseous(intraosseous: String){
        requireActivity().apply {
            findViewById<TextView>(R.id.intraosseous_spinner).text = intraosseous
        }
    }*/

/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController())
    }*/


}
