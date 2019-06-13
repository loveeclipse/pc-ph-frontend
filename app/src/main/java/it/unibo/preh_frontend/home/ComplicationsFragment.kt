package it.unibo.preh_frontend.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import it.unibo.preh_frontend.R

class ComplicationsFragment : Fragment() {

    private val sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complications, container, false)
    }


}
