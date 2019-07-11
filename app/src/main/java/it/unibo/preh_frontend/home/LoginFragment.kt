package it.unibo.preh_frontend.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import it.unibo.preh_frontend.R
import android.widget.ArrayAdapter
import it.unibo.preh_frontend.utils.DtIdentifiers
import it.unibo.preh_frontend.utils.RetrofitClient

class LoginFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val medicSpinner = root.findViewById<Spinner>(R.id.medicSpinner)
        val vehicleSpinner = root.findViewById<Spinner>(R.id.vehicleSpinner)

        var adapter = ArrayAdapter.createFromResource(requireContext(), R.array.medicSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        medicSpinner.adapter = adapter

        adapter = ArrayAdapter.createFromResource(requireContext(), R.array.vehicleSpinnerItems, R.layout.spinner_layout)
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout)
        vehicleSpinner.adapter = adapter

        root.findViewById<Button>(R.id.confirmButton).setOnClickListener {
            setDoctorAndVehicle("Dott. " + medicSpinner.selectedItem.toString(), vehicleSpinner.selectedItem.toString())
            DtIdentifiers.vehicle = vehicleSpinner.selectedItem.toString()
            DtIdentifiers.doctor = medicSpinner.selectedItem.toString()
            // Obtain eventUuid and missionUuid
            // Update medic to missionService
            RetrofitClient.getOngoingMissionsByVehicle()
            findNavController().navigate(R.id.action_login_to_home)
        }
        return root
    }

    private fun setDoctorAndVehicle(doctor: String, vehicle: String) {
        requireActivity().apply {
            findViewById<TextView>(R.id.doctor).text = doctor
            findViewById<TextView>(R.id.vehicle).text = vehicle
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().apply {
            findViewById<TextView>(R.id.title).text = getString(R.string.app_name)
            findViewById<TextView>(R.id.title).visibility = View.VISIBLE
            findViewById<TextView>(R.id.doctor).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.vehicle).visibility = View.INVISIBLE
            findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
            findViewById<Button>(R.id.finish).visibility = View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController())
    }
}
