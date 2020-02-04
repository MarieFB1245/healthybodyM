package com.example.healthy_body.calculate

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class delectdata_private_excercise(val id_excercise:String,val UID:String,val name_excercise:String,val kcal:String){

    fun delectex():Boolean {
        val myRef = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${UID}").child("${id_excercise}")
        myRef.removeValue()

        return true
    }
}