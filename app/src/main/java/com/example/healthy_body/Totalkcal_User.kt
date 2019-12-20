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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totalkcal__user)
        myRef = FirebaseDatabase.getInstance().reference



        val TDEEshow = findViewById<TextView>(R.id.statusname)
        val Statusshow = findViewById<TextView>(R.id.numberkcal)
        var UID: String = intent.getStringExtra("uid")

   Log.d("information","uid intent:$UID")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val map = dataSnapshot.value as Map<*, *>?
                val map1 = dataSnapshot.child("users").child(UID).value as Map<*, *>?
                val status = map1!!["status"].toString()
                val TDEE = map1["TDEE"].toString()
                TDEEshow.text = status
                Statusshow.text = TDEE
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        start.setOnClickListener {
            startintent(UID)
        }

    }


    fun startintent (UID:String){
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("uid",UID)
        startActivity(intent)
    }
}
