package com.example.healthy_body.calculate

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class dateselect_totalvalue (val UID : String,val date:String){

    val myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${UID}").child("$date")


    fun callvalue(callback: (excercise:String,food:String) -> Unit){
        
    }
}