package it.unibo.preh_frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {}

}
