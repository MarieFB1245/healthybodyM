package com.example.healthy_body

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.healthy_body.calculate.savemenuexcercise
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addexcercise_user.*
import kotlinx.android.synthetic.main.activity_home__user.*

class addexcercise_user : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    var UID :String=""
var backtohome:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addexcercise_user)
        inputkcal.setTransformationMethod(null)
        UID = intent.getStringExtra("UID")
        if(intent.getStringExtra("backtohome")!=null) backtohome =  intent.getStringExtra("backtohome")



        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {

            if(backtohome !=""){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, selectlistexcercise_user::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("back_home_add",backtohome)
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, selectlistexcercise_user::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }

        }
        val inputnameexcercise = findViewById<EditText>(R.id.inputnameexcercise)
        val inputkcal = findViewById<EditText>(R.id.inputkcal)

        buttonaddexceercisee.setOnClickListener {
            val textnameEX = inputnameexcercise.text.toString()
            val textkcal = inputkcal.text.toString()
            if (textnameEX !=""&&textkcal!=""){
                savemenuexcercise(textnameEX,textkcal).saveex()
                val progest  = ProgressDialog(this@addexcercise_user,R.style.MyTheme)
                progest.setCancelable(false)
                progest.show()
                Handler().postDelayed({
                    progest.cancel()


                if(backtohome !=""){
                    val backtohome = "homeselectexcercise"
                    val intent = Intent(this, selectlistexcercise_user::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("back_home_add",backtohome)
                    startActivity(intent)
                    finish()

                }else{
                    val intent = Intent(this, selectlistexcercise_user::class.java)
                    intent.putExtra("UID",UID)
                    startActivity(intent)
                    finish()

                }

                }, 1500)
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
        if(backtohome !=""){
            val backtohome = "homeselectexcercise"
            val intent = Intent(this, selectlistexcercise_user::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("back_home_add",backtohome)
            startActivity(intent)
            finish()

        }else{
            val intent = Intent(this, selectlistexcercise_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()

        }

    }
}
