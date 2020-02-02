package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.modelsavefood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class savemenuexcercise_private(val UID :String ,val textnameEx :String ="",val textkcal :String =""){

    val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE")

    var textnameex = textnameEx
    var textkcalupdate = textkcal
    var uid = UID
    fun saveex (){

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    for(list in p0.children){
                        val s = list!!.getValue(dataex::class.java)
                        val idEx = s!!.id_excercise
                        var nameEx = s!!.name_excercise
                        val kcalEx = s!!.kcal
                        Log.e("nameEx","${nameEx}")
                        Log.e("textinput","${textnameex}")

                        val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${uid}")
                        val q = ref.orderByKey().limitToLast(1)
                        q.addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                            }
                            override fun onDataChange(p0: DataSnapshot) {
                                Log.e("pass =>","ELSE")
                                Log.e("nameEx","${nameEx}")
                                for (list in p0.children){
                                    val s = list.getValue(dataex::class.java)
                                    Log.e("s =>","${s}")
                                    val stringkey = s!!.id_excercise
                                    val intkey = stringkey!!.toInt()
                                    Log.e("intkey","${intkey}")
                                    val newkey = intkey + 1
                                    Log.e("newkey","${newkey}")
                                    val id_food = newkey.toString()
                                    val model = dataex(id_food,textnameEx,textkcal)
                                    ref.child("${id_food}").setValue(model)
                                }



                            }
                        })


                    }


                }else{
                    val key: Int = 0
                    val newkey = key + 1
                    val id_excercise = newkey.toString()
                    val model = dataex(id_excercise,textnameex,textkcalupdate)
                    ref.child("${uid}").child("${id_excercise}").setValue(model)
                }
            }
        })
    }


}