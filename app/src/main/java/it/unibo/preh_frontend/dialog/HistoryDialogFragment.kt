package it.unibo.preh_frontend.dialog


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment

import it.unibo.preh_frontend.R
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog


class HistoryDialogFragment : DialogFragment() {

    private var aList: ArrayList<String> = ArrayList()
    private lateinit var mAdapter: ArrayAdapter<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var parentDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root =  inflater.inflate(R.layout.fragment_history_dialog, container, false)
        parentDialog = dialog!!

        val storiaList = root.findViewById(R.id.history_list) as ListView

        sharedPreferences = requireContext().getSharedPreferences("historyData", Context.MODE_PRIVATE)
        val savedSet = sharedPreferences.getStringSet("historyList", null)
        if(savedSet != null) {
            aList.addAll(savedSet.asIterable())
        }else {

        }
        mAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, aList)
        storiaList.adapter = mAdapter

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.history_image_button)
        saveAndExitButton.setOnClickListener {
            val builder1 = AlertDialog.Builder(requireContext())
            builder1.setMessage("Vuoi uscire senza salvare?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                    "Si"
            ) { dialog, id ->
                dialog.cancel()
                parentDialog.dismiss()
            }
            builder1.setNegativeButton(
                    "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }
        return root
    }

    override fun onCancel(dialog: DialogInterface) {
        aList.add(aList[aList.size-1]+"n")  //SIMULAZIONE AGGIUNTA STORICO
        mAdapter.notifyDataSetChanged()
        val saveDataSet = aList.toHashSet()
        sharedPreferences.edit().putStringSet("historyList",saveDataSet).apply()
        dialog.cancel()
    }

    override fun onResume() {
        super.onResume()
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels)
        val height = (metrics.heightPixels)
        dialog!!.window!!.setLayout(9 * width / 10,height)
    }


}
