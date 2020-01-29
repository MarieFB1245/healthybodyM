package com.example.healthy_body.calculate

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.healthy_body.Home_User
import com.example.healthy_body.model.User
import com.example.healthy_body.model.useredit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat
import kotlin.math.pow

class editinformation(val UID:String,val firstname :String ,val lastname : String ,val age : String ,val weight :String ,val height:String,val genderE :String ,val Level_Workout :String) {

    private lateinit var myRef: DatabaseReference

    val heightf = java.lang.Float.valueOf(height)
    val weigthf = java.lang.Float.valueOf(weight)
    val agef = java.lang.Float.valueOf(age)


    fun edit():Boolean {

        val df = DecimalFormat("0.00")
        val Reheight = heightf / 100f
        val totalBMI = weigthf / (Reheight.pow(2))
        val BMIS = df.format(totalBMI)
        val BMI = java.lang.Float.valueOf(BMIS)

        val myRef = FirebaseDatabase.getInstance().getReference("users").child("${UID}")

        //หาBMI
        if (BMI < 23) {
            val status = "ปกติ"
            val childUpdates = HashMap<String, String>()
            childUpdates.put("status", "${status}")
            myRef.updateChildren(childUpdates as Map<String, Any>)


        } else if (BMI >= 23 && BMI <= 25) {
            val status = "เริ่มอ่วน"
            val childUpdates = HashMap<String, String>()
            childUpdates.put("status", "${status}")
            myRef.updateChildren(childUpdates as Map<String, Any>)

        } else if (BMI >= 25 && BMI <= 30) {
            val status = "อ่วน"
            val childUpdates = HashMap<String, String>()
            childUpdates.put("status", "${status}")
            myRef.updateChildren(childUpdates as Map<String, Any>)

        } else {
            val status = "อ่วนมาก"
            val childUpdates = HashMap<String, String>()
            childUpdates.put("status", "${status}")
            myRef.updateChildren(childUpdates as Map<String, Any>)

        }


        if (genderE == "ชาย") {
            val result = 66 + (13.7f * weigthf) + (5 * heightf) - (6.8f * agef)
            val BMR = Math.round(result)
            val childUpdates = HashMap<String, Int>()
            childUpdates.put("BMR", BMR)
            myRef.updateChildren(childUpdates as Map<String, Any>)

            //หาค่า TDEE
            if (Level_Workout == "น้อย หรือไม่ค่อยออกกำลังกาย") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 1-3 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 4-5 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "หนัก ออกกำลังกาย 6-7 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            }


        } else {
            val result = 665f + (9.6f * weigthf) + (1.8f * heightf) - (4.7f * agef)
            var BMR = Math.round(result)
            val childUpdates = HashMap<String, Int>()
            childUpdates.put("BMR", BMR)
            myRef.updateChildren(childUpdates as Map<String, Any>)


            //หาค่า TDEE
            if (Level_Workout ==  "น้อย หรือไม่ค่อยออกกำลังกาย") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 1-3 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 4-5 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else if (Level_Workout == "หนัก ออกกำลังกาย 6-7 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                val childUpdates = HashMap<String, Long>()
                childUpdates.put("TDEE", TDEE)
                myRef.updateChildren(childUpdates as Map<String, Any>)


            }
        }

        val childUpdates = HashMap<String, Any>()
        childUpdates.put("uid", UID)
        childUpdates.put("textfirstname", firstname)
        childUpdates.put("textlastname", lastname)
        childUpdates.put("gender", genderE)
        childUpdates.put("level_Workout", Level_Workout)
        childUpdates.put("bmis", BMIS)
        childUpdates.put("weigth", weigthf)
        childUpdates.put("height", heightf)
        childUpdates.put("age", agef)
        myRef.updateChildren(childUpdates as Map<String, Any>)


return true
    }


}