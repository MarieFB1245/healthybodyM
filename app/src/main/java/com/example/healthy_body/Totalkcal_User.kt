package com.example.healthy_body

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register__infirmation.*
import kotlinx.android.synthetic.main.activity_totalkcal__user.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Totalkcal_User : AppCompatActivity() {
    private var myAut = FirebaseAuth.getInstance()
    private lateinit var myRef: DatabaseReference
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totalkcal__user)
        myRef = FirebaseDatabase.getInstance().reference


       // val TDEEshow = findViewById<TextView>(R.id.statusname)
        val numberkcalBMR = findViewById<TextView>(R.id.numberkcalBMR)
        val numberkcalTDEE = findViewById<TextView>(R.id.numberkcalTDEE)
       var UID: String = intent.getStringExtra("UID")
       var status: String = intent.getStringExtra("status")
       var TDEE: String = intent.getStringExtra("TDEE")
        var BMR: String = intent.getStringExtra("BMR")

 //  Log.d("information","uid intent:$UID")

        if(status == "ปกติ"){
            val layout = findViewById<TextView>(R.id.s1)
            val params = layout.getLayoutParams()
            params.height = 120 //px
            layout.setLayoutParams(params)
            s1.setTypeface(s1.getTypeface(), Typeface.BOLD)
        }else if (status == "เริ่มอ้วน"){
            val layout = findViewById<TextView>(R.id.s2)
            val params = layout.getLayoutParams()
            params.height = 120
            layout.setLayoutParams(params)
            s2.setTypeface(s2.getTypeface(), Typeface.BOLD);
        }else if (status == "อ้วน"){
            val layout = findViewById<TextView>(R.id.s3)
            val params = layout.getLayoutParams()
            params.height = 120
            layout.setLayoutParams(params)
            s3.setTypeface(s3.getTypeface(), Typeface.BOLD);
        }else{
            val layout = findViewById<TextView>(R.id.s4)
            val params = layout.getLayoutParams()
            params.height = 120
            layout.setLayoutParams(params)
            s4.setTypeface(s4.getTypeface(), Typeface.BOLD);
        }

       // TDEEshow.text = "test"
       // numberkcalBMR.text = "test"
      //  numberkcalTDEE.text = "test"

        //TDEEshow.text = status
        numberkcalBMR.text = BMR
        numberkcalTDEE.text = TDEE

       start.setOnClickListener {
            startintent(UID)
        }

    }

    fun startintent (UID:String){
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("UID",UID)
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
            .setContentText("ว่าต้องการออกจากระบบ!")
            .setCancelText("ไม่ต้องการ!")
            .setConfirmText("ต้องการ!")
            .showCancelButton(true)
            .setCancelClickListener { sDialog -> sDialog.cancel()
                this.doubleBackToExitPressedOnce = false
            }
            .setConfirmClickListener {
                val intent = Intent(this, Login_user::class.java)
                myAut.signOut()
                startActivity(intent)
                finish()
            }
            .show()


    }

}
