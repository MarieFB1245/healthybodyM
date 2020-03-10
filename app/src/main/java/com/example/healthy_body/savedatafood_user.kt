package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.healthy_body.calculate.dataex
import com.example.healthy_body.calculate.savetotalkcal
import com.example.healthy_body.model.modelSelectExcercise
import com.example.healthy_body.model.modelSelectFood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class savedatafood_user : AppCompatActivity(), View.OnClickListener {
    var nameFoodShowB :String=""
    var kcalfoodShowB :String=""
    var sum = 1
    var resultBig :Int=0
    var UID :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savedatafood_user)

        supportActionBar?.title ="Show Select Food"
        UID = intent.getStringExtra("UID")
        var nameFoodShow: String = intent.getStringExtra("namefood")
        var kcalfoodShow: String = intent.getStringExtra("kcalfood")
        var idfoodShow: String = intent.getStringExtra("id")

        nameFoodShowB = nameFoodShow
        kcalfoodShowB = kcalfoodShow
        val calendar = Calendar.getInstance()
        var currentDate = DateFormat.getDateInstance().format(calendar.time)
        var datetextview =  findViewById<TextView>(R.id.date)
        datetextview.setText(currentDate)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)
        val amount = findViewById<TextView>(R.id.amount)
        val tatal = findViewById<TextView>(R.id.tatal)

        var sumkcal = kcalfoodShowB.toInt()
        resultBig = sumkcal
        amount.setText("${sum}")
        tatal.setText("$sumkcal")
        add.setOnClickListener(this)
        sub.setOnClickListener(this)
        namefood.setText(nameFoodShow)
        Kcal.setText(kcalfoodShow)


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        savelist.setOnClickListener {
            val nametype :String= "FOOD"
            val nametypeStatus :String = "SAVE"
            val statusdoting :String = ""
            val date =""
            savetodata(nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDate,idfoodShow)
            savetotalkcal(resultBig,nametype,UID,statusdoting,nametypeStatus,date).savetotal()
            val intent = Intent(this,selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)

        }

    }
    fun savetodata(nameFoodShowB: String, kcalfoodShowB: String, resultBig: Int, sum: Int,currentDate:String,id:String) {
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDater = sdf.format(Date())
        Log.d("output","${currentDate}")
        val ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child("$currentDater")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val q = ref.orderByKey().limitToLast(1)
                    q.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (list in p0.children){

                                val d = list.getKey()
                                val intkey = d!!.toInt()
                                val newkey = intkey + 1
                                val id_list = newkey.toString()
                                val settext = modelSelectFood(id_list,nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDater,id)
                                ref.child("${id_list}").setValue(settext)
                            }



                        }
                    })
                }else{
                    val key: Int = 0
                    val newkey = key + 1
                    val id_list = newkey.toString()
                    val settext = modelSelectFood(id_list,nameFoodShowB,kcalfoodShowB,resultBig,sum,currentDater,id)
                    ref.child("${id_list}").setValue(settext)
                }
            }
        })


    }
    override fun onClick(v: View?) {
        Log.d("kcalfoodShow","${nameFoodShowB}")
        Log.d("kcalfoodShow","${kcalfoodShowB}")
        var sumkcalsub = kcalfoodShowB.toInt()
        Log.d("kcalfoodShow","${sumkcalsub}")
        var sumkcal = kcalfoodShowB.toInt()

        when (v?.id) {
            R.id.add -> {
                sum = sum + 1
                resultBig = sumkcal*sum
                Log.d("sumkcal add =>","${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            R.id.sub -> {
                sum = sum - 1
                resultBig = resultBig - sumkcalsub
                Log.d("sumkcal sub =>","${resultBig}")
                Log.d("sum","${sum}")
                if(sum<=1){
                    sum = 1
                    resultBig=sumkcalsub
                }
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            else -> {
            }
        }
    }
}
class datafood (val id:String ="",val nameFoodShowB: String="",val kcalfoodShowB :String="",val sum :Int, val resultBig : Int ,val date :String ,val id_list :String )