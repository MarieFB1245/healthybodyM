package com.example.healthy_body.calculate

import android.util.Log
import com.example.healthy_body.model.modelsavefood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class savemenufood_private(val UID :String ,var namefood:String="",val kcal:String="",val unit:String="",val unittype:String="",val amount :Int){

    val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${UID}")
    val namefoodS = namefood
    val kcalS = kcal
    val unitS = unit
    val unittypeS = unittype
    val amountS = amount
    val uid = UID
    fun save(){

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for(list in p0.children) {
                        val s = list!!.getValue(data::class.java)
                        val key = s!!.id_food
                        val namefood = s!!.namefood
                        val amount = s!!.amount
                        val kcal = s!!.kcal
                        val util = s!!.unit
                        val unittype = s!!.unittype

                        val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${uid}")
                        val q = ref.orderByKey().limitToLast(1)
                        q.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                for (list in p0.children) {
                                    val s = list.getValue(data_private::class.java)
                                    val stringkey = s!!.id_food
                                    val intkey = stringkey!!.toInt()
                                    val newkey = intkey + 1
                                    val id_food = newkey.toString()
                                    val model = modelsavefood(
                                        id_food,
                                        namefoodS,
                                        kcalS,
                                        unitS,
                                        unittypeS,
                                        amountS.toString()
                                    )
                                    ref.child("${id_food}").setValue(model)
                                }


                            }
                        })

                    }
                }else {
                    val amount = amount.toString()
                    val key: Int = 0
                    val newkey = key + 1
                    val id_food = newkey.toString()
                    val model = modelsavefood(id_food,namefood,kcal,unit,unittype,amount)
                    ref.child("${id_food}").setValue(model)
                }
            }
        })
    }


}
class data_private (val id_food:String ="",val namefood: String="",val amount: String="",val kcal :String="" ,val unit: String ="",val unittype: String="")