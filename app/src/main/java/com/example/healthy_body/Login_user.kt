package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_user.*
import android.text.method.PasswordTransformationMethod

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.InputType
import android.util.Log
import android.widget.ProgressBar
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import cn.pedant.SweetAlert.SweetAlertDialog

import java.util.regex.Pattern


class Login_user : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
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

            Log.e("email =>","${email}")
            Log.e("password =>","${password}")



            if(email==""&&password==""){
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("รหัสผิด")
                    .setContentText("กรุณาใส่ อีเมล เเละ รหัสผ่าน ให้ถูกต้อง!")
                    .setConfirmText("ตกลง")
                    .show()

            }else{
                    var progest  = ProgressDialog(this,R.style.MyTheme)
                    progest.setCancelable(false)
                    progest.show()
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {

                            var uid = FirebaseAuth.getInstance().uid?:""
                            login(uid)
                        } else {
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("รหัสผิด")
                                .setContentText("กรุณาใส่ อีเมล หรือ รหัสผ่าน ให้ถูกต้อง!")
                                .setConfirmText("ตกลง")
                                .show()
                            progest.cancel()
                        }
                    }


            }

        }
    }
    fun login(uid:String){
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("UID",uid)
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("คุณเเน่ใจ?")
            .setContentText("ว่าต้องการออกจากแอพลิเคชัน!")
            .setCancelText("ไม่ต้องการ!")
            .setConfirmText("ต้องการ!")
            .showCancelButton(true)
            .setCancelClickListener { sDialog -> sDialog.cancel()
                this.doubleBackToExitPressedOnce = false


            }
            .setConfirmClickListener {

                finish()
            }
            .show()


    }

}
