package it.unibo.preh_frontend.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.dialog.history.HistoryVitalParametersDialog
import it.unibo.preh_frontend.model.AnagraphicData
import it.unibo.preh_frontend.model.ComplicationsData
import it.unibo.preh_frontend.model.DrugsData
import it.unibo.preh_frontend.model.ManeuverData
import it.unibo.preh_frontend.model.NewPcCarData
import it.unibo.preh_frontend.model.PatientStatusData
import it.unibo.preh_frontend.model.PreHData
import it.unibo.preh_frontend.model.TreatmentData
import it.unibo.preh_frontend.model.VitalParametersData
import it.unibo.preh_frontend.utils.RuntimeTypeAdapterFactory

class VitalParametersDialog : HistoryVitalParametersDialog() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var savedState: VitalParametersData
    private lateinit var parentDialog: Dialog

    private lateinit var saveState: VitalParametersData
    private lateinit var localHistoryList: ArrayList<PreHData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_vital_parameters, container, false)
        parentDialog = dialog!!
        isCancelable = false
        dialog!!.setCanceledOnTouchOutside(false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        val historyType = object : TypeToken<ArrayList<PreHData>>() {}.type

        val typeFactory = RuntimeTypeAdapterFactory
                .of(PreHData::class.java, "type")
                .registerSubtype(AnagraphicData::class.java, "AnagraphicData")
                .registerSubtype(ComplicationsData::class.java, "ComplicationsData")
                .registerSubtype(ManeuverData::class.java, "ManeuverData")
                .registerSubtype(PatientStatusData::class.java, "PatientStatusData")
                .registerSubtype(TreatmentData::class.java, "TreatmentData")
                .registerSubtype(VitalParametersData::class.java, "VitalParametersData")
                .registerSubtype(DrugsData::class.java, "DrugsData")
                .registerSubtype(NewPcCarData::class.java, "NewPcCarData")

        val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(typeFactory).create()

        localHistoryList = gson.fromJson<ArrayList<PreHData>>(sharedPreferences.getString("historyList", null), historyType)

        getComponents(root)

        initSpinner()

        setSharedPreferences()

        val exitButton = root.findViewById<ImageButton>(R.id.parameters_image_button)
        exitButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setCancelable(true)
            if (checkEveryField()) {
                builder.apply {
                    setTitle("Conferma Parametri Vitali")
                    setMessage("I dati inseriti saranno salvati")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog.cancel()
                    }
                    setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                }
            } else {
                builder.apply {
                    setTitle("Uscire senza salvare?")
                    setMessage("Inserimento incompleto")
                    setPositiveButton("Si") { dialog, _ ->
                        dialog.cancel()
                        parentDialog.dismiss()
                    }
                    setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                }
            }
            val alert11 = builder.create()
            alert11.show()
        }

        return root
    }

    override fun initSpinner() {
        var newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.respiratoryFrequencyItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        freqRespiratoria.apply {
            adapter = newAdapter
            setSelection(1)
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.eyeOpeningItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        aperturaOcchi.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.verbalResponseItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        rispostaVerbale.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        newAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.motorResponseItems, R.layout.spinner_layout)
        newAdapter.setDropDownViewResource(R.layout.spinner_layout)
        rispostaMotoria.apply {
            adapter = newAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val gcs = "GCS = " + calculateGCS()
                    gcsTextView.text = gcs
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun checkEveryField(): Boolean {
        return (vieAeree.checkedRadioButtonId != -1 &&
                saturazione.text.toString() != "" &&
                freqCaridaca.text.toString() != "" &&
                tipoBattito.checkedRadioButtonId != -1 &&
                presArteriosa.text.toString() != "" &&
                tempRiempCapillare.checkedRadioButtonId != -1 &&
                colorCuteMucose.checkedRadioButtonId != -1 &&
                pupilleSx.checkedRadioButtonId != -1 &&
                pupilleDx.checkedRadioButtonId != -1 &&
                tempCorporea.text.toString() != "")
    }

    private fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSaveState = gson.fromJson(sharedPreferences.getString("vitalParameters", null), VitalParametersData::class.java)
            if (newSaveState != null) {
                this.activity!!.runOnUiThread {
                    vieAeree.check(newSaveState.vieAeree)
                    freqRespiratoria.setSelection(newSaveState.frequenzaRespiratoria)
                    saturazione.setText(newSaveState.saturazionePeriferica.toString())
                    freqCaridaca.setText(newSaveState.frequenzaCaridaca.toString())
                    tipoBattito.check(newSaveState.tipoBattito)
                    presArteriosa.setText(newSaveState.pressioneArteriosa.toString())
                    tempRiempCapillare.check(newSaveState.tempoRiempimentoCapillare)
                    colorCuteMucose.check(newSaveState.coloritoCuteMucose)
                    aperturaOcchi.setSelection(newSaveState.aperturaOcchi)
                    rispostaVerbale.setSelection(newSaveState.rispostaVerbale)
                    rispostaMotoria.setSelection(newSaveState.rispostaMotoria)
                    pupilleSx.check(newSaveState.pupilleSx)
                    pupilleDx.check(newSaveState.pupilleDx)
                    fotoreagenteSx.isChecked = newSaveState.fotoreagenteSx
                    fotoreagenteDx.isChecked = newSaveState.fotoreagenteDx
                    tempCorporea.setText(newSaveState.temperature.toString())
                }
                savedState = newSaveState
            }
        }).start()
    }

    override fun onCancel(dialog: DialogInterface) {
            saveState = VitalParametersData(vieAeree.checkedRadioButtonId,
                    freqRespiratoria.selectedItemPosition,
                    saturazione.text.toString().toInt(),
                    freqCaridaca.text.toString().toInt(),
                    tipoBattito.checkedRadioButtonId,
                    presArteriosa.text.toString().toInt(),
                    tempRiempCapillare.checkedRadioButtonId,
                    colorCuteMucose.checkedRadioButtonId,
                    aperturaOcchi.selectedItemPosition,
                    rispostaVerbale.selectedItemPosition,
                    rispostaMotoria.selectedItemPosition,
                    pupilleSx.checkedRadioButtonId,
                    pupilleDx.checkedRadioButtonId,
                    fotoreagenteSx.isChecked,
                    fotoreagenteDx.isChecked,
                    tempCorporea.text.toString().toDouble()
            )
            val gson = Gson()
            val stateAsJson = gson.toJson(saveState)
            sharedPreferences.edit().putString("vitalParameters", stateAsJson).apply()
            localHistoryList.add(saveState)
            val historyType = object : TypeToken<ArrayList<PreHData>>() {
            }.type
            val historyListAsJson = gson.toJson(localHistoryList, historyType)
            sharedPreferences.edit().putString("historyList", historyListAsJson).apply()
            super.onCancel(dialog)
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

    override fun calculateGCS(): Int {
        return 4 - aperturaOcchi.selectedItemPosition +
                5 - rispostaMotoria.selectedItemPosition +
                6 - rispostaVerbale.selectedItemPosition
    }
}