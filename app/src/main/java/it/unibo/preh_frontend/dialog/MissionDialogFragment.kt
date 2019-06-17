package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.home.AnagraficFragment
import it.unibo.preh_frontend.home.EventInfoFragment
import it.unibo.preh_frontend.model.AnagraphicData

class MissionDialogFragment : DialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog
    private lateinit var anagraficFragment: AnagraficFragment
    private lateinit var localAnagraphicData: AnagraphicData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_mission, container, false)
        parentDialog = dialog!!
        dialog!!.setCanceledOnTouchOutside(false)
        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        val manager = childFragmentManager
        val transaction = manager.beginTransaction()
        anagraficFragment = AnagraficFragment()
        transaction.replace(R.id.mission_tabFrame, anagraficFragment)
        transaction.commit()

        val exitAndSaveButton = root.findViewById<ImageButton>(R.id.mission_image_button)
        exitAndSaveButton.setOnClickListener {
                sharedPreferences.edit().remove("anagraphicDataSnapshot").apply()
                parentDialog.cancel()
        }

        val missionTabs = root.findViewById<TabLayout>(R.id.mission_tabs)
        missionTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val newFragment: Fragment
                when (missionTabs.selectedTabPosition) {
                    0 -> {
                        newFragment = AnagraficFragment()
                        anagraficFragment = newFragment
                    }
                    1 -> {
                        newFragment = EventInfoFragment()
                        saveSharedPreferencesSnapshot()
                    }
                    else -> {
                        newFragment = AnagraficFragment()
                        anagraficFragment = newFragment
                    }
                }
                val newTransaction = manager.beginTransaction()
                newTransaction.replace(R.id.mission_tabFrame, newFragment)
                newTransaction.addToBackStack(null)
                newTransaction.commit()
            }
        })
        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        Thread(Runnable {
            val saveState = anagraficFragment.getAnagraphicData()
            val gson = Gson()
            val anagraphicAsJson = gson.toJson(saveState)
            sharedPreferences.edit().putString("anagraphicData", anagraphicAsJson).apply()
        }).start()
        super.onCancel(dialog)
    }

    private fun saveSharedPreferencesSnapshot() {
        Thread(Runnable {
            val saveState = anagraficFragment.getAnagraphicData()
            val gson = Gson()
            val anagraphicAsJson = gson.toJson(saveState)
            sharedPreferences.edit().putString("anagraphicDataSnapshot", anagraphicAsJson).apply()
        }).start()
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
