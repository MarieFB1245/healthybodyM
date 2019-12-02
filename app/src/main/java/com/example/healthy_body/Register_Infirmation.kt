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
import kotlin.math.ceil


class Register_Infirmation : AppCompatActivity() {

    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__infirmation)

        var radiogroup = findViewById<RadioGroup>(R.id.radiogroup)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)


        registerbutton.setOnClickListener {

            var value = radiogroup.checkedRadioButtonId
            var radioG = findViewById<RadioButton>(value)
            val df = DecimalFormat("0.00")
            val resultSPIN = betterSpinner.text.toString()
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



            Log.d("information","resultSPIN:$resultSPIN")
            Log.d("information","age:$age")
            Log.d("information","gender :$gender")

             if(gender == "ชาย"){
                 Log.e("information","pass")
                 val result = 66+(13.7f*weigth)+(5*height)-(6.8f*age)
                 val BMR = Math.round(result)
                 //var textBMR = df.format(result)
                 val resultTDEE = (BMR * 1.55 )
                 val TDEE = Math.round(resultTDEE)
                 val reTDEE = resultTDEE - 500
                 val TDEEer = Math.round(reTDEE)
                 Log.d("information","BMR:$BMR")
                 Log.d("information","reTDEE:$TDEEer")
                 Log.d("information","TDEE:$TDEE")


               // genderboy(weigth,height,age)



            }else{
                val BMR = 665f +(9.6f * weigth)+(1.8f * height)-(4.7*age)
                Log.d("information","BMRหญิง:$BMR Kcal")
            }


            //หาBMI
            if(BMI < 23){
                var status = "ปหติ"
                Log.d("information","ร่างกายปกติ = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
                Log.d("information","$height")
            }else if( BMI >= 23 && BMI <=25){
                var status = "เริ่มอ่วน"
                Log.d("information","ร่างกายเริ่มอ่วน = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }else if( BMI >= 25 && BMI <=30) {
                var status = "อ่วน"
                Log.d("information", "ร่างกายอ่วน = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }else{
                var status = "อ่วนมาก"
                Log.d("information", "ร่างกายอ่วนมาก = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }

            //หาBMR




            val intent = Intent(this, Totalkcal_User::class.java)
            startActivity(intent)

        }



}

   /* fun genderboy(weigth:Float,height:Float,age:Float){
        val result = 66+(13.7f*weigth)+(5*height)-(6.8f*age)
        val df = DecimalFormat("0,000")
        df.roundingMode = RoundingMode.CEILING
        val textBMR = df.format(result)
        val number = java.lang.Float.valueOf(textBMR)
        Log.d("information","BMRชาย:$textBMR Kcal")
        Log.d("information","number:$number Kcal")
    }*/

    /*fun testTDEE(result:Float,resultSPIN:String){
        val df = DecimalFormat("0,000")
     val textBMR = df.format(result)
        val BMR = java.lang.Float.valueOf(textBMR)
        if(resultSPIN == "normal workout to 4-5 time a week"){
            val df = DecimalFormat("0,000")
            val resultTDEE = 500-(BMR * 1.55)
            val TDEE = df.format(resultTDEE)
            Log.d("information","TDEE:$TDEE Kcal")
        }
    }*/


}
