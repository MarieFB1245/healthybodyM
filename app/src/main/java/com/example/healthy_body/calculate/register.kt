package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat
import kotlin.math.pow


class register(val uid:String,val email :String,val password :String,val age :Float,val height:Float,val weigth:Float,val BMIS :String,val gender: String,val Level_Workout:String,val textfirstname:String,val textlastname:String) {


    private var myRef = FirebaseAuth.getInstance()


    fun regis() {

        val df = DecimalFormat("0.00")
        val Reheight = height / 100f
        val totalBMI = weigth / (Reheight.pow(2))
        val BMIS = df.format(totalBMI)
        val BMI = java.lang.Float.valueOf(BMIS)

        val myRef = FirebaseDatabase.getInstance().getReference("/users/$uid")


        val user = user(
            uid,
            email,
            password,
            textfirstname,
            textlastname,
            gender,
            Level_Workout,
            BMIS,
            weigth,
            height,
            age
        )

        myRef.setValue(user)

        //หาBMI
        if (BMI < 23) {
            val status = "ปกติ"
            myRef.child("status").setValue(status)


        } else if (BMI >= 23 && BMI <= 25) {
            val status = "เริ่มอ้วน"
            myRef.child("status").setValue(status)

        } else if (BMI >= 25 && BMI <= 30) {
            val status = "อ้วน"
            myRef.child("status").setValue(status)

        } else {
            val status = "อ้วนมาก"
            myRef.child("status").setValue(status)

        }



        if (gender == "ชาย") {
            val result = 66 + (13.7f * weigth) + (5 * height) - (6.8f * age)
            val BMR = Math.round(result)
            myRef.child("BMR").setValue(BMR)

            //หาค่า TDEE
            if (Level_Workout == "น้อย หรือไม่ค่อยออกกำลังกาย") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE

                myRef.child("TDEE").setValue(TDEE)


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 1-3 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)

            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 4-5 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)
                Log.d("information", "TDEE :$TDEE")


            } else if (Level_Workout == "หนัก ออกกำลังกาย 6-7 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)

            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)


            }


        } else {
            val result = 665f + (9.6f * weigth) + (1.8f * height) - (4.7f * age)
            var BMR = Math.round(result)
            myRef.child("BMR").setValue(BMR)

            //หาค่า TDEE
            if (Level_Workout == "น้อย หรือไม่ค่อยออกกำลังกาย") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)
                Log.d("information", "TDEE :$TDEE")


            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 1-3 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)

            } else if (Level_Workout == "ปานกลาง ออกกำลังกาย 4-5 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)


            } else if (Level_Workout == "หนัก ออกกำลังกาย 6-7 ครั้งต่อสัปดาห์") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)


            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE
                myRef.child("TDEE").setValue(TDEE)


            }
        }
    }
}


