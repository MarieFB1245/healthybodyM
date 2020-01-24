package com.example.healthy_body.calculate

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.example.healthy_body.model.get_dattauser_edit
import com.example.healthy_body.model.modellistfood

import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class selectdata_totalkcal (val uid:String){
    val sdf = SimpleDateFormat("dd-M-yyyy")
    val currentDate = sdf.format(Date())
   val myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${uid}").child("$currentDate")

    fun getdatatotal(callback: (excercise:String,food:String) -> Unit){


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
 class totalkcallare (var TOTALEXCERCISE:Int=0 ,var TOTALFOOD: Int=0)




