package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register__user.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView
import cn.pedant.SweetAlert.SweetAlertDialog


class Register_User : AppCompatActivity() {

    private var myRef = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__user)
        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, Login_user::class.java)
            startActivity(intent)
        }

        val button1 = findViewById(R.id.button1) as Button
        button1.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val conpassword = conpassword.text.toString()

            val passwordInt = password.length
            val conpasswordInt = conpassword.length

          // myRef = FirebaseAuth.getInstance()

            if(email!=""&&password!=""&&conpassword!=""){
                if(!isEmailValid(email)){
                    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("เกิดข้อผิดพลาด")
                        .setContentText("กรุณาใส่ อีเมล ให้ถูกต้อง!")
                        .setConfirmText("ตกลง")
                        .show()
                }else{
                    if( passwordInt < 6 && conpasswordInt < 6){
                        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("รหัสผ่านไม่ถูกต้อง")
                            .setContentText("กรุณาใส่รหัสผ่านให้มากกว่า 6 ตัว!")
                            .setConfirmText("ตกลง")
                            .show()

                    }else{
                        if(password==conpassword){
                            saveusertodatabase(email,password)
                        }
                        else{
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("รหัสผ่านไม่ตลงกัน")
                                .setContentText("กรุณาใส่รหัสผ่านให้ถูกต้อง!")
                                .setConfirmText("ตกลง")
                                .show()
                        }
                    }
                }


            }else{
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("เกิดข้อผิดพลาด")
                    .setContentText("กรุณาใส่ ข้อมูล ให้ถูกต้อง!")
                    .setConfirmText("ตกลง")
                    .show()
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
    fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}

