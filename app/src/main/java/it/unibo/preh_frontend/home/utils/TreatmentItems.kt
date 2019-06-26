package it.unibo.preh_frontend.home.utils

import android.view.View
import android.widget.Button
import android.widget.Spinner
import it.unibo.preh_frontend.R

class TreatmentItems {
    private lateinit var adrenalinButton: Button
    private lateinit var shockButton: Button
    private lateinit var resuscitationButton: Button

    lateinit var jawSubluxationButton: Button
    private lateinit var guedelButton: Button
    private lateinit var cricothyrotomyButton: Button
    lateinit var trachealTubeButton: Button

    private lateinit var oxygenTherapyButton: Button
    private lateinit var ambuButton: Button
    private lateinit var minithoracotomySxButton: Button
    private lateinit var minithoracotomyDxButton: Button
    lateinit var ippvButton: Button

    lateinit var peripheralSpinner: Spinner
    lateinit var centralSpinner: Spinner
    lateinit var intraosseousSpinner: Spinner
    private lateinit var peripheralButton: Button
    private lateinit var centralButton: Button
    private lateinit var intraosseousButton: Button

    fun getComponents(root: View) {
        root.apply {

            adrenalinButton = findViewById(R.id.adrenaline_button)
            shockButton = findViewById(R.id.shock_button)
            resuscitationButton = findViewById(R.id.cpr_button)

            jawSubluxationButton = findViewById(R.id.subluxation_button)
            guedelButton = findViewById(R.id.guedel_button)
            cricothyrotomyButton = findViewById(R.id.tirotomy_button)
            trachealTubeButton = findViewById(R.id.tracheal_tube_button)

            oxygenTherapyButton = findViewById(R.id.oxigen_therapy_button)
            ambuButton = findViewById(R.id.ambu_button)
            minithoracotomySxButton = findViewById(R.id.minitoracotomiaSx_button)
            minithoracotomyDxButton = findViewById(R.id.minithoracotomyDx_button)
            ippvButton = findViewById(R.id.ippv_button)

            peripheralSpinner = findViewById(R.id.peripheric_spinner)
            centralSpinner = findViewById(R.id.central_spinner)
            intraosseousSpinner = findViewById(R.id.intraosseous_spinner)
            peripheralButton = findViewById(R.id.peripheric_button)
            centralButton = findViewById(R.id.central_button)
            intraosseousButton = findViewById(R.id.intraosseous_button)
        }
    }
}