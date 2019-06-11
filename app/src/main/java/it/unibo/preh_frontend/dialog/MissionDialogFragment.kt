package it.unibo.preh_frontend.dialog


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.home.AnagraficFragment
import it.unibo.preh_frontend.home.EventInfoFragment






class MissionDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_mission, container, false)

        val manager = childFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.mission_tabFrame,AnagraficFragment())
        transaction.commit()

        val exitAndSaveButton = root.findViewById<ImageButton>(R.id.mission_image_button)
        exitAndSaveButton.setOnClickListener{
            //TODO SAVE THE CURRENT STATE AND EXIT
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
                    0 -> newFragment = AnagraficFragment()
                    1 -> newFragment = EventInfoFragment()
                    else -> {
                        newFragment = AnagraficFragment()
                    }
                }
                val newTransaction = manager.beginTransaction()
                newTransaction.replace(R.id.mission_tabFrame, newFragment)
                newTransaction.commit()
            }
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10,height)
    }


}
