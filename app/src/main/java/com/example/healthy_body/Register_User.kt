package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register_User : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__user)


        val button1 = findViewById(R.id.button1) as Button
        button1.setOnClickListener {
            val intent = Intent(this, Register_Infirmation::class.java)
            startActivity(intent)
        }
    }
}
