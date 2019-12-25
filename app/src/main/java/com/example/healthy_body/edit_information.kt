package com.example.healthy_body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class edit_information : AppCompatActivity() {
    //uid firebase
    val UID = "HUIMcfHX1Ic5WtGi067oKz83BIX2"
    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()

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


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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

               // val height = java.lang.Float.valueOf(map1["age"].toString())
                //val weigth = java.lang.Float.valueOf(map1!!["weight"].toString())
                //val age = java.lang.Float.valueOf(map1["height"].toString())
                inputfirstname.setText(firstname)
                inputLastname.setText(lastname)
                inputage.setText(age)
                inputweight.setText(weight)
                inputheight.setText(height)
                //radioG.setText(gender)
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