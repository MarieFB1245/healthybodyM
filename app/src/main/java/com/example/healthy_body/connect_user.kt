package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import java.util.*
import kotlin.concurrent.schedule

class connect_user : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_user)

            if (haveNetwork()){
                Timer("secon", false).schedule(5000) {
                    Intent()

                }

            } else if (!haveNetwork()) {

                val test = SweetAlertDialog(this@connect_user, SweetAlertDialog.ERROR_TYPE)
                test.setTitleText("เกิดข้อผิดพลาด")
                test.setContentText("กรุณาตรวจสอบอินเทอร์เน็ตของคุณ!")
                test.setConfirmText("ตกลง")
                test.setCancelable(false)
                test.setConfirmClickListener { finish() }
                test.show()

            }




    }
    private fun haveNetwork(): Boolean {
        var have_WIFI = false
        var have_MobileData = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) have_WIFI =
                true
            if (info.typeName.equals(
                    "MOBILE DATA",
                    ignoreCase = true
                )
            ) if (info.isConnected) have_MobileData = true
        }
        return have_WIFI || have_MobileData
    }

    internal fun Intent(){
        val intent = Intent(this,Login_user::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = false
        finish()



    }
}
