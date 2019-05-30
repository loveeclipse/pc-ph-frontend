package it.unibo.preh_frontend.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.unibo.preh_frontend.R

class HomeFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        /* val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

        }*/
        root.findViewById<View>(R.id.button1).setOnClickListener {
            DialogFragmentExample().show(requireActivity().supportFragmentManager, "dialog_fragment")
        }
        root.findViewById<View>(R.id.button2).setOnClickListener {
            BottomSheetFragmentExample().show(requireActivity().supportFragmentManager, "bottom_dialog_fragment")
        }
        root.findViewById<Button>(R.id.button3).setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setMessage("banana 2")
                    .setTitle("Decidi")
                    .setPositiveButton("OK") { dialog, which ->
                        Toast.makeText(requireContext(), "premuto ok", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        Toast.makeText(requireContext(), "premuto annulla", Toast.LENGTH_SHORT).show()
                    }
                    .create().show()
        }
        root.findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener {
                DialogFragmentExample().show(requireActivity().supportFragmentManager, "banana_dialog")
                //Toast.makeText(requireContext(), "banana", Toast.LENGTH_SHORT).show()
                //Snackbar.make(findViewById(R.id.root), "Sei sicuro", Snackbar.LENGTH_SHORT).show()
            }
        }

        val sharedPreferences = requireContext().getSharedPreferences("dati", Context.MODE_PRIVATE)

        sharedPreferences.edit().putString("chiave_valore", "valore").apply()

        val valoreSalvato = sharedPreferences.getString("chiave_valore", null)

        if (valoreSalvato != null && valoreSalvato == "valore")  {
            Toast.makeText(requireContext(), "i valori sono uguali", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController())
    }
}