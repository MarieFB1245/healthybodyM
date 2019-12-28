package com.example.healthy_body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner

class edit_information : AppCompatActivity() {
    //uid firebase
    val UID = "HUIMcfHX1Ic5WtGi067oKz83BIX2"
    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()
    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_information)


        myRef = FirebaseDatabase.getInstance().reference


        val inputfirstname = findViewById<EditText>(R.id.inputfirstname)
        val inputLastname = findViewById<EditText>(R.id.inputLastname)
        val inputage = findViewById<EditText>(R.id.inputage)
        val inputweight = findViewById<EditText>(R.id.inputweight)
        val inputheight = findViewById<EditText>(R.id.inputheight)
        var radiogroup = findViewById<RadioGroup>(R.id.radiogroup)


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
                betterSpinner.setAdapter(arrayAdapter)
                var value = radiogroup.checkedRadioButtonId
                var radioG = findViewById<RadioButton>(value)
var ra1 = findViewById<RadioButton>(R.id.ra1)
                var ra2 = findViewById<RadioButton>(R.id.ra2)
                val map = dataSnapshot.value as Map<*, *>?
                val map1 = dataSnapshot.child("users").child(UID).value as Map<*, *>?
                val firstname = map1!!["textfirstname"].toString()
                val lastname = map1!!["textlastname"].toString()
                val age = map1!!["age"].toString()
                val weight = map1!!["weigth"].toString()
                val height = map1!!["height"].toString()
                val gender = map1["gender"].toString()
                val betterSpinnerNostring = map1["level_Workout"].toString()

                inputfirstname.setText(firstname)
                inputLastname.setText(lastname)
                inputage.setText(age)
                inputweight.setText(weight)
                inputheight.setText(height)
                betterSpinner.setText(betterSpinnerNostring)

                if(gender == "ชาย"){
                    ra1.setChecked(true)

                }else{
                    ra2.setChecked(true)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}