package com.example.healthy_body.calculate

import android.util.Log
import com.google.firebase.database.*
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class savetotalkcal (val kcal:Int ,val nametype : String="",val UID :String="",val statusdoting:String="",val nametypeStatus:String = "",val date :String=""){

    private lateinit var myRef: DatabaseReference

    val uid = UID
    fun savetotal (){
if (nametype.equals("FOOD")){
    if(nametypeStatus.equals("SAVE")){
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${uid}").child("$currentDate").child("TOTALFOOD")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    Log.e("pass =>","IF")
                    val data = p0!!.getValue()
                    val kcaldata = data.toString()
                    val kcaldataFood = kcaldata.toInt()

                    if(kcaldataFood != null){
                        var totalkcal = kcaldataFood+kcal
                        myRef.setValue(totalkcal)
                    }else{
                        Log.e("ERROR =>","update")
                    }



                }else{
                    Log.e("pass =>","else")

                    myRef.setValue(kcal)

                }
            }
        })
    }else{
            myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${uid}").child("$date").child("TOTALFOOD")
            myRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        Log.e("pass =>","IF")
                        val data = p0!!.getValue()
                        val kcaldata = data.toString()
                        val kcaldataFood = kcaldata.toInt()

                        Log.e("statusdoting","${statusdoting}")


                        if(statusdoting =="Add"){
                            if(kcaldataFood != null){
                                var totalkcal = kcaldataFood+kcal
                                myRef.setValue(totalkcal)
                            }else{
                                Log.e("ERROR =>","update")
                            }
                        }
                        else if(statusdoting =="de"){

                            var totalkcal = kcaldataFood-kcal
                            myRef.setValue(totalkcal)
                        }
                        else{}

                    }else{
                        Log.e("pass =>","else")
                        myRef.setValue(kcal)

                    }
                }
            })



    }
}

else{
    if (nametype.equals("SAVE")){
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        val myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${uid}").child("$currentDate").child("TOTALEXCERCISE")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){

                    val data = p0!!.getValue()
                    val kcaldata = data.toString()
                    val kcaldataExcercise = kcaldata.toInt()

                    if(kcaldataExcercise != null){
                        var totalkcal = kcaldataExcercise+kcal
                        myRef.setValue(totalkcal)
                    }else{
                        Log.e("ERROR =>","update")
                    }
                }else{
                    myRef.setValue(kcal)
                }
            }
        })
    }else{

        myRef = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${uid}").child("$date").child("TOTALEXCERCISE")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    Log.e("pass =>","IF")
                    val data = p0!!.getValue()
                    val kcaldata = data.toString()
                    val kcaldataExcercise = kcaldata.toInt()

                    Log.e("statusdoting","${statusdoting}")


                    if(statusdoting =="Add"){
                        if(kcaldataExcercise != null){
                            var totalkcal = kcaldataExcercise+kcal
                            myRef.setValue(totalkcal)
                        }else{
                            Log.e("ERROR =>","update")
                        }
                    }
                    else if(statusdoting =="de"){
                        var totalkcal = kcaldataExcercise-kcal
                        myRef.setValue(totalkcal)
                    }
                    else{}

                }else{
                    Log.e("pass =>","else")
                    myRef.setValue(kcal)

                }
            }
        })



    }



}


    }
}


