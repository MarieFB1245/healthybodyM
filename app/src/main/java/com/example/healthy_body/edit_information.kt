package com.example.healthy_body


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.healthy_body.calculate.editinformation
import com.example.healthy_body.model.get_dattauser_edit

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_edit_information.*

class edit_information : AppCompatActivity() {
    var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    private lateinit var myRef: DatabaseReference
    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")
    internal var SPINNERLSTGENDER = arrayOf("ชาย","หญิง")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_information)




       // var UID: String = intent.getStringExtra("UID")
        val inputfirstname = findViewById<EditText>(R.id.inputfirstname)
        val inputLastname = findViewById<EditText>(R.id.inputLastname)
        val inputage = findViewById<EditText>(R.id.inputage)
        val inputweight = findViewById<EditText>(R.id.inputweight)
        val inputheight = findViewById<EditText>(R.id.inputheight)
        val arrayAdaptergender = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLSTGENDER)
        val betterSpinnergender = findViewById(R.id.spinner_gender) as MaterialBetterSpinner
        betterSpinnergender.setAdapter(arrayAdaptergender)

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)
        val Level_Workout = betterSpinner.text.toString()
        myRef = FirebaseDatabase.getInstance().getReference("users").child("${UID}")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userinfo = dataSnapshot.getValue(get_dattauser_edit::class.java)

                val firstname = userinfo?.textfirstname
                val lastname = userinfo?.textlastname
                val age = userinfo?.age
                val weight = userinfo?.weigth
                val height = userinfo?.height
                val gender = userinfo?.gender
                val betterSpinnerNostring = userinfo?.level_Workout

                val ageI = age!!.toInt()
                val weightI = weight!!.toInt()
                val heightI = height!!.toInt()

                val agestring: String = ageI.toString()
                val weightstring: String = weightI.toString()
                val heightstring: String = heightI.toString()



                inputfirstname.setText(firstname)
                inputLastname.setText(lastname)
                inputage.setText(agestring)
                inputweight.setText(weightstring)
                inputheight.setText(heightstring)
                betterSpinnergender.setText(gender)
                betterSpinner.setText(betterSpinnerNostring)


                editbutton.setOnClickListener {
                    val firstnameE: String = inputfirstname.text.toString()
                    val lastnameE: String = inputLastname.text.toString()
                    val ageE: String = inputage.text.toString()
                    val weightE: String = inputweight.text.toString()
                    val heightE: String = inputheight.text.toString()
                    val genderE: String = betterSpinnergender.text.toString()
                    val betterSpinnerNostringE: String = betterSpinner.text.toString()

                    if (firstnameE != "" && lastnameE != "" && ageE != "" && weightE != "" && heightE != "" && genderE != "" && betterSpinnerNostringE != "") {

                        var result = editinformation(
                            UID,
                            firstnameE,
                            lastnameE,
                            ageE,
                            weightE,
                            heightE,
                            genderE,
                            betterSpinnerNostringE
                        ).edit()
                        Log.e("result", "${result}")
                        if (result == true) {
                            pass(UID)
                        }
                    } else {

                   error()


                    }




            }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }

        })

        }

fun pass (uid:String=""){
    val intent = Intent(this,Home_User::class.java)
    intent.putExtra("UID",uid)
    startActivity(intent)

}

    fun error (){
        Toast.makeText(this, "Please in put information in correct.", Toast.LENGTH_SHORT).show()
    }

    }
