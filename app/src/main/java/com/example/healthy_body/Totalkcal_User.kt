package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register__infirmation.*
import kotlinx.android.synthetic.main.activity_totalkcal__user.*

class Totalkcal_User : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totalkcal__user)
        myRef = FirebaseDatabase.getInstance().reference


        val TDEEshow = findViewById<TextView>(R.id.statusname)
        val Statusshow = findViewById<TextView>(R.id.numberkcal)
        var UID: String = intent.getStringExtra("UID")
        var status: String = intent.getStringExtra("status")
        var TDEE: String = intent.getStringExtra("TDEE")

   Log.d("information","uid intent:$UID")




                TDEEshow.text = status
                Statusshow.text = TDEE


        start.setOnClickListener {
            startintent(UID)
        }

    }

    fun startintent (UID:String){
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()
    }


}
