package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_register__infirmation.*

import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IntegerRes
import java.math.RoundingMode

import kotlin.math.pow

import java.text.DecimalFormat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.text.CaseMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_totalkcal__user.*
import java.util.logging.Level
import kotlin.math.ceil
import kotlin.math.log


class Register_Infirmation : AppCompatActivity() {

    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")
    private var myRef = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__infirmation)

        var radiogroup = findViewById<RadioGroup>(R.id.radiogroup)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)


        registerbutton.setOnClickListener {
            var firstname  =  findViewById<EditText>(R.id.inputfirstname)
            var lastname  =  findViewById<EditText>(R.id.inputLastname)
            var textfirstname = firstname.text.toString()
            var textlastname = lastname.text.toString()
            var value = radiogroup.checkedRadioButtonId
            var radioG = findViewById<RadioButton>(value)
            val df = DecimalFormat("0.00")
            val Level_Workout = betterSpinner.text.toString()
            var textheight = findViewById<EditText>(R.id.inputheight)
            var textweigth = findViewById<EditText>(R.id.inputweight)
            var textage = findViewById<EditText>(R.id.inputage)
            val height = java.lang.Float.valueOf(textheight.getText().toString())
            val weigth = java.lang.Float.valueOf(textweigth.getText().toString())
            val age = java.lang.Float.valueOf(textage.getText().toString())
            val Reheight = height/100f
            val totalBMI= weigth/(Reheight.pow(2))
            val BMIS = df.format(totalBMI)
            val BMI = java.lang.Float.valueOf(BMIS)
            var gender  = radioG.text.toString()


            Log.d("information","firstname = $textfirstname")
            Log.d("information","lastname = $textlastname")
            //หาBMI
            if(BMI < 23){
                 val status = "ปหติ"
            }else if( BMI >= 23 && BMI <=25){
                val status = "เริ่มอ่วน"
            }else if( BMI >= 25 && BMI <=30) {
                val  status = "อ่วน"
            }else{
                val  status = "อ่วนมาก"
            }



             if(gender == "ชาย"){
                 Log.e("information","pass")
                 val result = 66+(13.7f*weigth)+(5*height)-(6.8f*age)
                 val BMR = Math.round(result)
                 Log.d("information","BMRชาย :$BMR")
                 //หาค่า TDEE
                 if(Level_Workout=="low workout"){
                     val resultTDEE = (BMR * 1.2 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")

                 }else if(Level_Workout=="normal workout to 1-3 time a week"){
                     val resultTDEE = (BMR * 1.375 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")

                 }else if (Level_Workout=="normal workout to 4-5 time a week"){
                     val resultTDEE = (BMR * 1.55 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")

                 }else if (Level_Workout=="heavy workout to 6-7 time a week"){
                     val resultTDEE = (BMR * 1.7 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")
                 }else{
                     val resultTDEE = (BMR * 1.9 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")
                 }



            }else{
                val result = 665f +(9.6f * weigth)+(1.8f * height)-(4.7f*age)
                 var BMR = Math.round(result)

                Log.d("information","BMRหญิง:$BMR Kcal")

                 //หาค่า TDEE
                 if(Level_Workout=="low workout"){
                     val resultTDEE = (BMR * 1.2 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")

                 }else if(Level_Workout=="normal workout to 1-3 time a week"){
                     val resultTDEE = (BMR * 1.375 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500

                     Log.d("information","TDEE :$TDEE")
                 }else if (Level_Workout=="normal workout to 4-5 time a week"){
                     val resultTDEE = (BMR * 1.55 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500

                     Log.d("information","TDEE :$TDEE")
                 }else if (Level_Workout=="heavy workout to 6-7 time a week"){
                     val resultTDEE = (BMR * 1.7 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")
                 }else{
                     val resultTDEE = (BMR * 1.9 )
                     val reTDEE = Math.round(resultTDEE)
                     val TDEE = reTDEE - 500
                     Log.d("information","TDEE :$TDEE")
                 }

             }


val user = User(textfirstname,textlastname,BMI,age,gender,Level_Workout)
            Log.d("information","User :$user")
            val uid = FirebaseAuth.getInstance().uid?:""
            val myRef= FirebaseDatabase.getInstance().getReference("/users/$uid")
            myRef.setValue(user)
                .addOnSuccessListener {
                    Log.e("register", "save to database")
                    val intent = Intent(this, Totalkcal_User::class.java)
                    startActivity(intent)
                }
        }

}

}
    class User(val textfirstname:String ,val textlastname:String,BMI:Float,age:Float,gender:String,Level_Workout:String)