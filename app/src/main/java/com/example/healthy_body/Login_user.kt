package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_user.*
import android.text.method.PasswordTransformationMethod
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.InputType













class Login_user : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)
        register.setOnClickListener{
            val intent = Intent(this, Register_User::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            var email = email.text.toString()
            var password = password.text.toString()
            if(email==""&&password==""){
                Toast.makeText(this, "please in put email and password", Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        var uid = FirebaseAuth.getInstance().uid?:""
                        login(uid)
                    } else {
                        Toast.makeText(this, "Email and Password is not Correct", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
    fun login(uid:String){
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("UID",uid)
        startActivity(intent)
    }


}
