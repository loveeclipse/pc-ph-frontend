package it.unibo.preh_frontend.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.ManeuverData

class DrugsFragment : Fragment() {

    private val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drugs, container, false)
    }

    override fun onStart() {
        val gson = Gson()
        val savedState = gson.fromJson(sharedPreferences.getString("maneuversData",null), ManeuverData::class.java)
        if(savedState != null){
            applySharedPreferences(savedState)
        }
        super.onStart()
    }

    private fun applySharedPreferences(savedState: ManeuverData){
    }

    /*fun getData():String{
        val gson = Gson()

    }*/


}
