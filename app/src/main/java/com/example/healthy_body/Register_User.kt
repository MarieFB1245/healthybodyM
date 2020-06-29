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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.database.DatabaseReference


class Register_User : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
   // private var
    private lateinit var auth: FirebaseAuth
    private lateinit var myReft: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__user)
        auth = FirebaseAuth.getInstance()
        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, Login_user::class.java)
            startActivity(intent)
            finish()
        }

        val button1 = findViewById(R.id.button1) as Button

        button1.setOnClickListener {
            var emailPattern ="^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$"
            val email = email.text.toString()
            val password = password.text.toString()
            val conpassword = conpassword.text.toString()

            val passwordInt = password.length
            val conpasswordInt = conpassword.length

            auth = FirebaseAuth.getInstance()


            if(email!=""&&password!=""&&conpassword!=""){
                if(!emailPattern.toRegex().matches(email)){
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

                            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener {result  ->

                               val test = result.getResult()!!.signInMethods
                                if(!test!!.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)){
                                    saveusertodatabase(email,password)
                                }else{
                                    SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("อีเมลนี้ถูกใช้งานเเล้ว")
                                        .setContentText("กรุณาใสอีเมลอื่น!")
                                        .setConfirmText("ตกลง")
                                        .show()
                                }

                            }

                        }
                        else{
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("รหัสผ่านไม่ตรงกัน")
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
        finish()

    }
    fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
                val intent = Intent(this, Login_user::class.java)
                startActivity(intent)
                finish()



    }
}

