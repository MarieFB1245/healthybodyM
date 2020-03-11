package com.example.healthy_body.calculate

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class dateselect_totalvalue (val UID : String,val date:String){

    val myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${UID}").child("$date")


    fun callvalue(callback: (excercise:String,food:String) -> Unit){


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    val userinfo = p0.getValue(totalkcallare::class.java)
                    val excercise = userinfo!!.TOTALEXCERCISE.toString()
                    val food = userinfo!!.TOTALFOOD.toString()

                    if(excercise != ""){
                        val food = "0"
                        callback.invoke(excercise,food)
                    }else if (food !=""){
                        val excercise = "0"
                        callback.invoke(excercise,food)
                    }else{
                        callback.invoke(excercise,food)
                    }

                    Log.d("listtotal", excercise)
                    Log.d("listtotal", food)
                    callback.invoke(excercise,food)

                }else{
                    val excercise = "0"
                    val food = "0"
                    callback.invoke(excercise,food)
                }

            }
        })
    }
}