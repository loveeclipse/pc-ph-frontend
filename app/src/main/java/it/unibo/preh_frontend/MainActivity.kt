package it.unibo.preh_frontend

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import it.unibo.preh_frontend.utils.PermissionManager

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        setSupportActionBar(findViewById(R.id.toolbar))
        val endPreHButton = findViewById<Button>(R.id.finish)
        endPreHButton.setOnClickListener {
            // FINISCE L'EVENTO DI PREH, ELIMINA LE SHAREDPREFERENCES E FAI LE ULTIME OPERAZIONI

            val sharedPreferences = getSharedPreferences("preHData", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            navController.navigate(R.id.action_home_to_login)
        }
        PermissionManager.checkPermission(this, this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {}
}
