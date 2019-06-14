package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import it.unibo.preh_frontend.R
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson

class HistoryDialogFragment : DialogFragment() {

    private var aList: ArrayList<String> = ArrayList()
    private lateinit var mAdapter: ArrayAdapter<String>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history_dialog, container, false)
        dialog!!.setCanceledOnTouchOutside(false)
        val gson = Gson()

        val storiaList = root.findViewById(R.id.history_list) as ListView
        mAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, aList)
        storiaList.adapter = mAdapter

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)
        val newList = gson.fromJson(sharedPreferences.getString("historyList", null),ArrayList<String>()::class.java)
        if (newList != null) {
            aList.addAll(newList)
            mAdapter.notifyDataSetChanged()
        } else {
        }

        val saveAndExitButton = root.findViewById<ImageButton>(R.id.history_image_button)
        saveAndExitButton.setOnClickListener {
            dialog!!.dismiss()
        }
        return root
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
