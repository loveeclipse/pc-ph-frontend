package it.unibo.preh_frontend.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment

import it.unibo.preh_frontend.R
import android.widget.ArrayAdapter
import android.widget.ListView


class HistoryDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_history_dialog, container, false)


        val listView = root.findViewById<ListView>(R.id.history_list);
        val initialList = ArrayList<String>()
        val mAdapter =  ArrayAdapter(requireContext(), R.layout.abc_list_menu_item_layout, initialList)
        listView.adapter = mAdapter

        val buttonTest = root.findViewById<ImageButton>(R.id.imageButton4)
        buttonTest.setOnClickListener{
            mAdapter.add("balblalblalbla")

        }


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
