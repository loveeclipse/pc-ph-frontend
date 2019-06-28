package it.unibo.preh_frontend

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import it.unibo.preh_frontend.utils.PermissionManager
import it.unibo.preh_frontend.dialog.TerminatePreH
import it.unibo.preh_frontend.utils.CentralizationManager

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(findViewById(R.id.toolbar))
        val endPreHButton = findViewById<Button>(R.id.finish)
        endPreHButton.setOnClickListener {
            if (supportFragmentManager.findFragmentByTag("terminate_pre_h") == null)
                TerminatePreH().show(supportFragmentManager, "terminate_pre_h")
        }
        PermissionManager.checkPermission(this, this,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("preHData", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        CentralizationManager.centralizationIsActive = false

        navController.navigate(R.id.action_home_to_login)
    }
}
