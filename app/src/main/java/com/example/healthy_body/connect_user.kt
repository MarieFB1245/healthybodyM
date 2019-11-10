package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class connect_user : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_user)
        val b2 = findViewById(R.id.b2) as Button
        b2.setOnClickListener {
            val intent = Intent(this, Register_User::class.java)
            startActivity(intent)

        }
    }

}
