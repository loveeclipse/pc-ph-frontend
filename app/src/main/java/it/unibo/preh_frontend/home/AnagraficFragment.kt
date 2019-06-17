package it.unibo.preh_frontend.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import com.google.gson.Gson

import it.unibo.preh_frontend.R
import it.unibo.preh_frontend.model.AnagraphicData

class AnagraficFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var residenceEditText: EditText
    private lateinit var birthplaceEditText: EditText
    private lateinit var birthdayEditText: EditText

    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var anticoagulantsSwitch: Switch
    private lateinit var antiplateletsSwitch: Switch

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_anagrafic, container, false)

        sharedPreferences = requireContext().getSharedPreferences("preHData", Context.MODE_PRIVATE)

        nameEditText = root.findViewById(R.id.name_editText)
        surnameEditText = root.findViewById(R.id.surname_editText)
        residenceEditText = root.findViewById(R.id.residence_editText)
        birthdayEditText = root.findViewById(R.id.birthday_editText)
        birthplaceEditText = root.findViewById(R.id.birthplace_editText)

        genderRadioGroup = root.findViewById(R.id.gender_radiogroup)
        anticoagulantsSwitch = root.findViewById(R.id.anticoagulants_switch)
        antiplateletsSwitch = root.findViewById(R.id.antiplatelets_switch)

        return root
    }

    fun setSharedPreferences() {
        Thread(Runnable {
            val gson = Gson()
            val newSavedState = gson.fromJson(sharedPreferences.getString("anagraphicData", null), AnagraphicData::class.java)
                this.activity!!.runOnUiThread {
                    if (newSavedState != null) {
                        applySharedPreferences(newSavedState)
                    }
                }
            }
        ).start()
    }

    override fun onStart() {
        val gson = Gson()
        val snapshot = gson.fromJson(sharedPreferences.getString("anagraphicDataSnapshot", null), AnagraphicData::class.java)
        if (snapshot != null) {
            applySharedPreferences(snapshot)
        } else {
            setSharedPreferences()
        }
        super.onStart()
    }

    private fun applySharedPreferences(anagraphicData: AnagraphicData) {
        nameEditText.setText(anagraphicData.name)
        surnameEditText.setText(anagraphicData.surname)
        residenceEditText.setText(anagraphicData.residence)
        birthdayEditText.setText(anagraphicData.birthday)
        birthplaceEditText.setText(anagraphicData.birthplace)

        genderRadioGroup.check(anagraphicData.gender)
        anticoagulantsSwitch.isChecked = anagraphicData.anticoagulants
        antiplateletsSwitch.isChecked = anagraphicData.antiplatelets
    }

    fun getAnagraphicData(): AnagraphicData {
        return AnagraphicData(nameEditText.text.toString(),
                surnameEditText.text.toString(),
                residenceEditText.text.toString(),
                birthplaceEditText.text.toString(),
                birthdayEditText.text.toString(),
                genderRadioGroup.checkedRadioButtonId,
                anticoagulantsSwitch.isChecked,
                antiplateletsSwitch.isChecked,
                time = "qualche volta"
                )
    }
}
