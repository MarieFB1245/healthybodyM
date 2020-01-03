package com.example.healthy_body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.healthy_body.calculate.editinformation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_edit_information.*

class edit_information : AppCompatActivity() {


val UID = "WKtbyCKFPLfWdTtMLEbv5VYouuw1"

    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()
    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_information)


        myRef = FirebaseDatabase.getInstance().reference

        //var UID: String = intent.getStringExtra("UID")
        val inputfirstname = findViewById<EditText>(R.id.inputfirstname)
        val inputLastname = findViewById<EditText>(R.id.inputLastname)
        val inputage = findViewById<EditText>(R.id.inputage)
        val inputweight = findViewById<EditText>(R.id.inputweight)
        val inputheight = findViewById<EditText>(R.id.inputheight)
        var radiogroup = findViewById<RadioGroup>(R.id.radiogroup)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)

        val Level_Workout = betterSpinner.text.toString()
       var value = radiogroup.checkedRadioButtonId


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                test(dataSnapshot)
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }

            fun test(dataSnapshot: DataSnapshot) {
                val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner


                var ra1 = findViewById<RadioButton>(R.id.ra1)
                var ra2 = findViewById<RadioButton>(R.id.ra2)
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

                if (gender == "ชาย") {
                  //  genderint =
                // value = gender
                    var radioG = findViewById<RadioButton>(value)
                } else {
                    ra2.setChecked(true)
                }

                editbutton.setOnClickListener {
                    editinformation(
                        UID,
                        firstname,
                        lastname,
                        age,
                        weight,
                        height,
                        gender,
                        Level_Workout
                    ).edit()
                }
            }
        })

        }
       /*var firstname = inputfirstname.text.toString()
        var lastname = inputLastname.text.toString()
        var agef = java.lang.Float.valueOf(inputage.getText().toString())
        var weightf = java.lang.Float.valueOf(inputweight.getText().toString())
       var heightf = java.lang.Float.valueOf(inputheight.getText().toString())
        var genderE = radioG.text.toString()*/


    }

}