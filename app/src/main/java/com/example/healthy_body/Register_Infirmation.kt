package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_register__infirmation.*
import kotlinx.android.synthetic.main.activity_totalkcal__user.*
import java.util.*

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
            val text = betterSpinner.text.toString()
            Log.d("test","pass")
            test(text)
            val intent = Intent(this, Totalkcal_User::class.java)
            startActivity(intent)

        }
    }


  //  fun register (v: View){

    //    val text = betterSpinner.text.toString()
    //    Log.d("test","pass")

    // /   test(text)
  //      val intent = Intent(this, Totalkcal_User::class.java)
   //     startActivity(intent)
   // }

    fun test (text:String){
        Log.e("test", "index="+text)
    }
}
