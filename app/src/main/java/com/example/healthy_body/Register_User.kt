package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register__user.*

class Register_User : AppCompatActivity() {

    private var myRef = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__user)


        val button1 = findViewById(R.id.button1) as Button
        button1.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val conpassword = conpassword.text.toString()
          // myRef = FirebaseAuth.getInstance()

            if(email!=""&&password!=""&&conpassword!=""){

              //  if( passwordInt < 6 && conpasswordInt < 6){
                  //  Toast.makeText(this, "Please in put Password over 6 character.", Toast.LENGTH_SHORT).show()
            //    }else{
                   if(password==conpassword){
                      saveusertodatabase(email,password)

                   }
            else{
                       Toast.makeText(this, "Password and ConfrimPassword is not correct.", Toast.LENGTH_SHORT).show()
                     }
            //    }

            }else{
                Toast.makeText(this, "Please in put Email and Password.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun saveusertodatabase(email:String,password:String){
                Log.e("register","save to database")
                val intent = Intent(this, Register_Infirmation::class.java)
        intent.putExtra("email",email)
intent.putExtra("password",password)
                startActivity(intent)

    }
}

