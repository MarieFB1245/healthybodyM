package com.example.healthy_body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar



class setting_user : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_user)

        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        val actionBar = supportActionBar
        actionBar!!.title = "setting"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)

    }


}
