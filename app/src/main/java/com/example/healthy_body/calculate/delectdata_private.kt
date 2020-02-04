package com.example.healthy_body.calculate

import com.google.firebase.database.FirebaseDatabase

class delectdata_private(val id_food:String,val UID:String,val textnameF:String,val textkcalF:String,val textamountF:String,val textunitF:String,val texttypeunitF:String  ){

    fun deelect():Boolean {

        val myRef = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${UID}").child("${id_food}")
        myRef.removeValue()

        return true
    }
}