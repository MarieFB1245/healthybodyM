package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.User
import com.example.healthy_body.model.useredit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.DecimalFormat
import kotlin.math.pow

class editinformation(val UID:String,val firstname :String ,val lastname : String , val age : String ,val weight :String ,val height:String,val genderE :String ,val Level_Workout :String) {

    val heightf = java.lang.Float.valueOf(height)
    val weigthf = java.lang.Float.valueOf(weight)
    val agef = java.lang.Float.valueOf(age)


    fun edit() {

        val df = DecimalFormat("0.00")
        val Reheight = heightf / 100f
        val totalBMI = weigthf / (Reheight.pow(2))
        val BMIS = df.format(totalBMI)
        val BMI = java.lang.Float.valueOf(BMIS)

        val myRef = FirebaseDatabase.getInstance().getReference("/users/$UID")

        //หาBMI
        if (BMI < 23) {
            val status = "ปกติ"
            myRef.child("status").setValue(status)


        } else if (BMI >= 23 && BMI <= 25) {
            val status = "เริ่มอ่วน"
            myRef.child("status").setValue(status)

        } else if (BMI >= 25 && BMI <= 30) {
            val status = "อ่วน"
            myRef.child("status").setValue(status)

        } else {
            val status = "อ่วนมาก"
            myRef.child("status").setValue(status)

        }


        if (genderE == "ชาย") {
            val result = 66 + (13.7f * weigthf) + (5 * heightf) - (6.8f * agef)
            val BMR = Math.round(result)
            myRef.child("BMR").setValue(BMR)

            //หาค่า TDEE
            if (Level_Workout == "low workout") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500

                myRef.child("TDEE").setValue(TDEE)


            } else if (Level_Workout == "normal workout to 1-3 time a week") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)

            } else if (Level_Workout == "normal workout to 4-5 time a week") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)
                Log.d("information", "TDEE :$TDEE")


            } else if (Level_Workout == "heavy workout to 6-7 time a week") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)

            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)


            }


        } else {
            val result = 665f + (9.6f * weigthf) + (1.8f * heightf) - (4.7f * agef)
            var BMR = Math.round(result)
            myRef.child("BMR").setValue(BMR)

            //หาค่า TDEE
            if (Level_Workout == "low workout") {
                val resultTDEE = (BMR * 1.2)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)
                Log.d("information", "TDEE :$TDEE")


            } else if (Level_Workout == "normal workout to 1-3 time a week") {
                val resultTDEE = (BMR * 1.375)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)

            } else if (Level_Workout == "normal workout to 4-5 time a week") {
                val resultTDEE = (BMR * 1.55)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)


            } else if (Level_Workout == "heavy workout to 6-7 time a week") {
                val resultTDEE = (BMR * 1.7)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)


            } else {
                val resultTDEE = (BMR * 1.9)
                val reTDEE = Math.round(resultTDEE)
                val TDEE = reTDEE - 500
                myRef.child("TDEE").setValue(TDEE)


            }
        }
        val user = useredit(
            UID,
            firstname,
            lastname,
            genderE,
            Level_Workout,
            BMIS,
            weigthf,
            heightf,
            agef
        )

        myRef.setValue(user)
    }

}