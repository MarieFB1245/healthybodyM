package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.healthy_body.calculate.dataex
import com.example.healthy_body.calculate.savetotalkcal
import com.example.healthy_body.model.modelSelectExcercise
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_savedataexcercise_user.*
import kotlinx.android.synthetic.main.activity_savedataexcercise_user.Kcal
import kotlinx.android.synthetic.main.activity_savedataexcercise_user.nameexcercise
import kotlinx.android.synthetic.main.activity_savedataexcercise_user.savelist
import kotlinx.android.synthetic.main.activity_savedataexcercise_user_private.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user_private.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class savedataexcercise_user_private : AppCompatActivity(),View.OnClickListener {
    private var doubleBackToExitPressedOnce = false
    var nameExcerciseShowB :String=""
    var kcalExcerciseShowB :String=""
    var sum = 1
    var resultBig :Int=0
    var UID :String=""
    var backtohome:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savedataexcercise_user_private)

        supportActionBar?.title ="Show Select Food"
        UID = intent.getStringExtra("UID")
        if(intent.getStringExtra("backtohome")!=null) backtohome =  intent.getStringExtra("backtohome")


        var nameExcerciseShow: String = intent.getStringExtra("nameexcercise")
        var kcalExcerciseShow: String = intent.getStringExtra("kcalexcercise")
        var idfoodShow: String = intent.getStringExtra("id")


        nameExcerciseShowB = nameExcerciseShow
        kcalExcerciseShowB = kcalExcerciseShow
        val calendar = Calendar.getInstance()
        var  currentDate = DateFormat.getDateInstance().format(calendar.time)
        var datetextview =  findViewById<TextView>(R.id.date)
        datetextview.setText(currentDate)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)
        var amount = findViewById<TextView>(R.id.amountnum)
        var tatal = findViewById<TextView>(R.id.tatal)

        var sumkcal = kcalExcerciseShowB.toInt()
        resultBig = sumkcal
        amount.setText("${sum}")
        tatal.setText("$sumkcal")
        add.setOnClickListener(this)
        sub.setOnClickListener(this)
        nameexcercise.setText(nameExcerciseShow)
        Kcal.setText(kcalExcerciseShow)


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            if(backtohome !=""){
                val backtohome = "homeselectexcercise"
                val intent = Intent(this, selectlistexcercise_user_private::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, selectlistexcercise_user_private::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }


        }

        savelist.setOnClickListener {
            val nametype :String= "EXCERCISE"
            val nametypeStatus :String = "SAVE"
            val statusdoting :String = ""
            val date =""
            savetodata(nameExcerciseShowB,kcalExcerciseShowB,resultBig,sum,currentDate,idfoodShow)
            savetotalkcal(resultBig,nametype,UID,statusdoting,nametypeStatus,date).savetotal()

            val progest  = ProgressDialog(this@savedataexcercise_user_private,R.style.MyTheme)
            progest.setCancelable(false)
            progest.show()
            Handler().postDelayed({
                progest.cancel()

                if(backtohome !=""){
                    val backtohome = "homeselectexcercise"
                    val intent = Intent(this, selectlistexcercise_user::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("back_home_add",backtohome)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    startActivity(intent)
                    finish()

                }else{

                    val intent = Intent(this,selectlistexcercise_user::class.java)
                    intent.putExtra("UID",UID)
                    intent.putExtra("nametypeStatus",nametypeStatus)
                    startActivity(intent)
                    finish()

                }


            }, 1500)
        }

    }
    fun savetodata(nameExcerciseShowB: String, kcalExcerciseShowB: String, resultBig: Int, sum: Int,currentDate:String,id:String) {
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        Log.d("output","${currentDate}")
        val ref = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}").child("$currentDate")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    val ref = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}").child("$currentDate")
                    val q = ref.orderByKey().limitToLast(1)
                    q.addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (list in p0.children){
                                val s = list.getValue(dataex::class.java)
                                val d = list.getKey()
                                val intkey = d!!.toInt()
                                val newkey = intkey + 1
                                val id_list = newkey.toString()
                                val settext = modelSelectExcercise(id_list,nameExcerciseShowB,kcalExcerciseShowB,resultBig,sum,currentDate,id)
                                ref.child("${id_list}").setValue(settext)
                            }



                        }
                    })
                }else{
                    val key: Int = 0
                    val newkey = key + 1
                    val id_list = newkey.toString()
                    val settext = modelSelectExcercise(id_list,nameExcerciseShowB,kcalExcerciseShowB,resultBig,sum,currentDate,id)
                    ref.child("${id_list}").setValue(settext)
                }
            }
        })

    }
    override fun onClick(v: View?) {
        Log.d("kcalfoodShow","${nameExcerciseShowB}")
        Log.d("kcalfoodShow","${kcalExcerciseShowB}")
        var sumkcalsub = kcalExcerciseShowB.toInt()
        Log.d("kcalfoodShow","${sumkcalsub}")
        var sumkcal = kcalExcerciseShowB.toInt()
        var amount = findViewById<TextView>(R.id.amountnum)
        var tatal = findViewById<TextView>(R.id.tatal)
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
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        if(backtohome !=""){
            val backtohome = "homeselectexcercise"
            val intent = Intent(this, selectlistexcercise_user_private::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("backtohome",backtohome)
            startActivity(intent)
            finish()

        }else{

            val intent = Intent(this,selectlistexcercise_user_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()

        }


    }
}
