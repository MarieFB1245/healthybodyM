package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.healthy_body.calculate.savemenuexcercise_private
import kotlinx.android.synthetic.main.activity_addexcercise_user.*

class addexcercise_user_private_Bsetting : AppCompatActivity() {
    var UID :String=""
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addexcercise_user_private__bsetting)


        UID = intent.getStringExtra("UID")
        inputkcal.setTransformationMethod(null)
        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, list_edit_excercise_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }
        val inputnameexcercise = findViewById<EditText>(R.id.inputnameexcercise)
        val inputkcal = findViewById<EditText>(R.id.inputkcal)

        buttonaddexceercisee.setOnClickListener {
            val textnameEX = inputnameexcercise.text.toString()
            val textkcal = inputkcal.text.toString()
            if (textnameEX !=""&&textkcal!=""){
                savemenuexcercise_private(UID,textnameEX,textkcal).saveex()
                val intent = Intent(this, selectlistexcercise_user::class.java)
                intent.putExtra("UID", UID)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please in put Information Excercise", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, list_edit_excercise_private::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()

    }
}
