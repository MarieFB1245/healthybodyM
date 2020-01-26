package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.modelsaveexcercise
import com.example.healthy_body.model.modelsavefood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class savemenuexcercise(val textnameEx :String ="",val textkcal :String =""){

    val ref = FirebaseDatabase.getInstance().getReference("EXCERCISE")

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

                        if(textnameEx == nameEx){
                            val ref = FirebaseDatabase.getInstance().getReference("EXCERCISE").child(idEx)
                            val childUpdates = HashMap<String, String>()
                            childUpdates.put("name_excercise", "${textnameEx}")
                            childUpdates.put("kcal", "${textkcal}")
                            ref.updateChildren(childUpdates as Map<String, Any>)
                            nameEx=""
                        }else{
                            val key = list!!.key
                            val intkey = key!!.toInt()
                            val newkey = intkey + 1
                            val id_food = newkey.toString()
                            val model = dataex(id_food,textnameEx,textkcal)
                            ref.child("${id_food}").setValue(model)
                        }

                    }
                }else{
                    val key: Int = 0
                    val newkey = key + 1
                    val id_excercise = newkey.toString()
                    val model = dataex(id_excercise,textnameEx,textkcal)
                    ref.child("${id_excercise}").setValue(model)
                }
            }
        })
    }

}
class dataex (val id_excercise:String ="",val name_excercise: String="",val kcal :String="" )