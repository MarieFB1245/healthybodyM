package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Totalkcal_User : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totalkcal__user)

    }
    fun start (v: View){
        val intent = Intent(this, Home_User::class.java)
        startActivity(intent)
    }
}
