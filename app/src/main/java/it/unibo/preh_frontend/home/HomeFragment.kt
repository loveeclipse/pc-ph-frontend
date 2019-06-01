package it.unibo.preh_frontend.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import it.unibo.preh_frontend.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.replace(R.id.tabFrame, DrugsFragment())
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
                val transaction = manager!!.beginTransaction()
                transaction.replace(R.id.tabFrame, newFragment)
                transaction.commit()
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })
        return root
    }
}
