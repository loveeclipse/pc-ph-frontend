package it.unibo.preh_frontend

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import it.unibo.preh_frontend.utils.PermissionManager
import it.unibo.preh_frontend.dialog.TerminatePreH
import it.unibo.preh_frontend.utils.RetrofitClient

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.obtainServiceLocation()
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
        // DISABLED
    }
}
