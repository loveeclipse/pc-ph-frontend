package it.unibo.preh_frontend.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.unibo.preh_frontend.dialog.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import it.unibo.preh_frontend.R

class HomeFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val patientStatusButton = root.findViewById<Button>(R.id.stato_paziente_button)
        patientStatusButton.setOnClickListener{
            PatientStatusDialogFragment().show(requireActivity().supportFragmentManager, "patient_status_dialog_fragment")
        }
        val missionButton = root.findViewById<Button>(R.id.dett_missione_button)
        missionButton.setOnClickListener{
            MissionDialogFragment().show(requireActivity().supportFragmentManager, "mission_dialog_fragment")
        }

        val pcCarButton = root.findViewById<Button>(R.id.pcCar_button)
        pcCarButton.setOnClickListener{
           //NewPcCarBottomSheetFragment().show(requireActivity().supportFragmentManager, "bottom_dialog_fragment")
            NewPcCarDialogFragment().show(requireActivity().supportFragmentManager, "pcCar_dialog_fragment")
        }
        val noteButton = root.findViewById<Button>(R.id.note_button)
        noteButton.setOnClickListener{
            //NewPcCarBottomSheetFragment().show(requireActivity().supportFragmentManager, "bottom_dialog_fragment")
            NoteDialogFragment().show(requireActivity().supportFragmentManager, "note_dialog_fragment")
        }
        val vitalParameterButton = root.findViewById<Button>(R.id.param_vitali_button)
        vitalParameterButton.setOnClickListener{
            VitalParametersDialogFragment().show(requireActivity().supportFragmentManager, "vital_parameters_fragment")
        }

        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.replace(R.id.home_tabFrame, DrugsFragment())
        transaction.commit()

        val homeTabs = root.findViewById<TabLayout>(R.id.home_tabs)
        homeTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val newFragment: Fragment
                when (homeTabs.selectedTabPosition) {
                    0 -> newFragment = DrugsFragment()
                    1 -> newFragment = ManeuverFragment()
                    2 -> newFragment = TreatmentFragment()
                    3 -> newFragment = ComplicationsFragment()
                    else -> {
                        newFragment = DrugsFragment()
                    }
                }
                val newTransaction = manager.beginTransaction()
                newTransaction.replace(R.id.home_tabFrame, newFragment)
                newTransaction.commit()
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().apply {
            findViewById<TextView>(R.id.title).visibility = View.VISIBLE
            findViewById<TextView>(R.id.doctor).visibility = View.VISIBLE
            findViewById<TextView>(R.id.vehicle).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.alert).visibility = View.VISIBLE
            findViewById<Button>(R.id.finish).visibility = View.VISIBLE
        }

    }
}
