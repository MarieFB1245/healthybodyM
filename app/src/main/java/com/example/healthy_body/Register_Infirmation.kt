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
import androidx.annotation.IntegerRes

import kotlin.math.pow

import java.text.DecimalFormat



class Register_Infirmation : AppCompatActivity() {

    internal var SPINNERLST = arrayOf("low workout","normal workout to 1-3 time a week","normal workout to 4-5 time a week",
        "heavy workout to 6-7 time a week","heaviest workout over to 2 time a day")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__infirmation)


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)


        registerbutton.setOnClickListener {
            val df = DecimalFormat("0.00")
            val resultSPIN = betterSpinner.text.toString()
            var textheight = findViewById<EditText>(R.id.inputheight)
            var textweigth = findViewById<EditText>(R.id.inputweight)
            val height = java.lang.Float.valueOf(textheight.getText().toString())
            val weigth = java.lang.Float.valueOf(textweigth.getText().toString())
            val Reheight = height/100f
            val totalBMI= weigth/(Reheight.pow(2))
            val BMIS = df.format(totalBMI)
            val BMIF = java.lang.Float.valueOf(BMIS)
            if(BMIF < 23){
                Log.d("information","ร่างกายปกติ = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }else if( BMIF >= 23 && BMIF <=25){
                Log.d("information","ร่างกายเริ่มอ่วน = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }else if( BMIF >= 25 && BMIF <=30) {
                Log.d("information", "ร่างกายอ่วน = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }else{
                Log.d("information", "ร่างกายอ่วนมาก = $BMIS")
                Log.d("information","$Reheight")
                Log.d("information","$weigth")
            }




         //   Log.d("infomation","$heightF")

             // Log.d("age4","$BMI")
            //  if (resultSPIN == "low workout"){

            //  }
            val intent = Intent(this, Totalkcal_User::class.java)
            startActivity(intent)

        }


    }



}
