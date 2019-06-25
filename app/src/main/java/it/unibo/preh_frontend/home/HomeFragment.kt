package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.PatientStatusDialogFragment
import it.unibo.preh_frontend.dialog.HistoryDialogFragment
import it.unibo.preh_frontend.dialog.MissionDialogFragment
import it.unibo.preh_frontend.dialog.NewPcCarDialogFragment
import it.unibo.preh_frontend.dialog.NoteDialogFragment
import it.unibo.preh_frontend.dialog.VitalParametersDialog
import it.unibo.preh_frontend.model.PreHData
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var previousDisplayedFragment: Fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        val gson = Gson()
        val historyType = object : TypeToken<ArrayList<PreHData>>() {
        }.type
        val historyListAsJson = gson.toJson(ArrayList<PreHData>(), historyType)

        sharedPreferences.edit().putString("historyList", historyListAsJson).apply()

        root.findViewById<Button>(R.id.patient_status_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("patient_status_dialog_fragment") == null)
                PatientStatusDialogFragment().show(requireActivity().supportFragmentManager, "patient_status_dialog_fragment")
        }

        root.findViewById<Button>(R.id.history_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("history_dialog_fragment") == null)
                HistoryDialogFragment().show(requireActivity().supportFragmentManager, "history_dialog_fragment")
        }

        root.findViewById<Button>(R.id.mission_details_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("mission_dialog_fragment") == null)
                MissionDialogFragment().show(requireActivity().supportFragmentManager, "mission_dialog_fragment")
        }

        root.findViewById<Button>(R.id.pcCar_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("pcCar_dialog_fragment") == null)
                NewPcCarDialogFragment().show(requireActivity().supportFragmentManager, "pcCar_dialog_fragment")
        }
        root.findViewById<Button>(R.id.note_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("note_dialog_fragment") == null)
                NoteDialogFragment().show(requireActivity().supportFragmentManager, "note_dialog_fragment")
        }
        root.findViewById<Button>(R.id.vital_parameters_button).setOnClickListener {
            if (requireActivity().supportFragmentManager.findFragmentByTag("vital_parameters_fragment") == null)
                VitalParametersDialog().show(requireActivity().supportFragmentManager, "vital_parameters_fragment")
        }

        val manager = fragmentManager
        val transaction = manager?.beginTransaction()
        val initialFragment = DrugsFragment()
        previousDisplayedFragment = initialFragment
        transaction?.apply {
            replace(R.id.home_tabFrame, initialFragment)
            commit()
        }

        val homeTabs = root.findViewById<TabLayout>(R.id.home_tabs)
        homeTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val newFragment: Fragment
                when (homeTabs.selectedTabPosition) {
                    0 -> {
                        newFragment = DrugsFragment()
                    }
                    1 -> {
                        newFragment = ManeuverFragment()
                        savePreviousFragment()
                    }
                    2 -> {
                        newFragment = TreatmentFragment()
                        savePreviousFragment()
                    }
                    3 -> {
                        newFragment = ComplicationsFragment()
                        savePreviousFragment()
                    }
                    else -> {
                        newFragment = DrugsFragment()
                    }
                }
                val newTransaction = manager?.beginTransaction()
                newTransaction?.apply {
                    replace(R.id.home_tabFrame, newFragment)
                    commit()
                }
                previousDisplayedFragment = newFragment
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
            findViewById<ImageView>(R.id.alert).visibility = View.INVISIBLE
            findViewById<Button>(R.id.finish).visibility = View.VISIBLE
        }
    }

    private fun savePreviousFragment() {
        val gson = Gson()
        when (previousDisplayedFragment::class.java) {
            ManeuverFragment::class.java -> {
                val previousFragment = previousDisplayedFragment as ManeuverFragment
                val saveData = gson.toJson(previousFragment.getData())
                sharedPreferences.edit().putString("maneuversData", saveData).apply()
            }
            TreatmentFragment::class.java -> {
                val previousFragment = previousDisplayedFragment as TreatmentFragment
                val saveData = gson.toJson(previousFragment.getData())
                sharedPreferences.edit().putString("treatmentData", saveData).apply()
            }
            ComplicationsFragment::class.java -> {
                val previousFragment = previousDisplayedFragment as ComplicationsFragment
                val saveData = gson.toJson(previousFragment.getData())
                sharedPreferences.edit().putString("complicationsData", saveData).apply()
            }
        }
    }
}
