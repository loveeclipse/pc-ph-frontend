package it.unibo.preh_frontend.home


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData

class DrugsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_drugs, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        return root
    }


}
