package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.healthy_body.calculate.data
import com.example.healthy_body.calculate.savetotalkcal
import com.example.healthy_body.model.modelSelectFood
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_list_saveedit_food.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.amount
import kotlinx.android.synthetic.main.activity_savedatafood_user.tatal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class list_saveedit_food : AppCompatActivity(), View.OnClickListener {

    var nameFoodShowB: String = ""
    var kcalfoodShowB: String = ""
    var sum = 0
    var resultBig: Int = 0
    var UID: String = ""
    var idfoodShow: String = ""
    var newsum : Int = 0
    var statusdoting :String = ""
    var date :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_food)
        var add = findViewById<Button>(R.id.add)
        var sub = findViewById<Button>(R.id.sub)
        UID = intent.getStringExtra("UID")
        nameFoodShowB = intent.getStringExtra("nameFoodShowB")
        kcalfoodShowB = intent.getStringExtra("kcalfoodShowB")
        resultBig = intent.getIntExtra("resultBig", resultBig)
        sum = intent.getIntExtra("sum", sum)
        date = intent.getStringExtra("date")
        idfoodShow = intent.getStringExtra("id")

        val textdate = findViewById<TextView>(R.id.date)
        val textanmefood = findViewById<TextView>(R.id.namefood)
        val textkcal = findViewById<TextView>(R.id.Kcal)
        val textamount = findViewById<TextView>(R.id.amount)
        val texttotal = findViewById<TextView>(R.id.tatal)

        add.setOnClickListener(this)
        sub.setOnClickListener(this)


        textdate.setText(date)
        textanmefood.setText(nameFoodShowB)
        textkcal.setText(kcalfoodShowB)
        textamount.setText("${sum}")
        texttotal.setText("${resultBig}")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }


        Editlist.setOnClickListener {
            val nametype :String= "FOOD"
            val nametypeStatus :String = "Edit"
            updatetodata(nameFoodShowB,kcalfoodShowB,resultBig,sum,date,idfoodShow)
            savetotalkcal(newsum,nametype,UID,statusdoting,nametypeStatus,date).savetotal()
            val intent = Intent(this,selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)

        }

    }

    private fun updatetodata(nameFoodShowB: String?, kcalfoodShowB: String?, resultBig: Int, sum: Int, currentDate: String, idfoodShow: String) {

        val ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child("$currentDate").child("$idfoodShow")
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("sum", sum)
        childUpdates.put("resultBig", resultBig)
        ref.updateChildren(childUpdates as Map<String, Any>)

    }

    override fun onClick(v: View?) {
        Log.d("kcalfoodShow", "${nameFoodShowB}")
        Log.d("kcalfoodShow", "${kcalfoodShowB}")
        var sumkcalsub = kcalfoodShowB.toInt()
        Log.d("kcalfoodShow", "${sumkcalsub}")
        var sumkcal = kcalfoodShowB.toInt()

        when (v?.id) {
            R.id.add -> {
                sum = sum + 1
                statusdoting = "Add"
                newsum = newsum + sumkcal
                resultBig = sumkcal * sum
                Log.d("sumkcal add =>", "${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            R.id.sub -> {
                sum = sum - 1
                statusdoting = "de"
                newsum = newsum + sumkcal
                resultBig = resultBig - sumkcalsub

                if(sum<=1){
                    sum = 1
                    resultBig=sumkcalsub
                }
                Log.d("sumkcal sub =>", "${resultBig}")
                Log.d("sum", "${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            else -> {

            }
        }

    }
}
