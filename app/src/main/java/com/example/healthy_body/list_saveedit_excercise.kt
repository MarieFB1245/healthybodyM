package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.healthy_body.calculate.savetotalkcal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_list_saveedit_food.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.amount
import kotlinx.android.synthetic.main.activity_savedatafood_user.tatal
import java.util.*

class list_saveedit_excercise : AppCompatActivity(), View.OnClickListener {


    var nameExcerciseShowB: String = ""
    var kcalExcerciseShowB: String = ""
    var sum = 0
    var resultBig: Int = 0
    var UID: String = ""
    var idfoodShow: String = ""
    var newsum: Int = 0
    var statusdoting: String = ""
    var date: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_excercise)
        var add = findViewById<Button>(R.id.add)
        var sub = findViewById<Button>(R.id.sub)

        UID = intent.getStringExtra("UID")
        nameExcerciseShowB = intent.getStringExtra("nameExcerciseShowB")
        kcalExcerciseShowB = intent.getStringExtra("kcalExcerciseShowB")
        resultBig = intent.getIntExtra("resultBig", resultBig)
        sum = intent.getIntExtra("sum", sum)
        date = intent.getStringExtra("date")
        idfoodShow = intent.getStringExtra("id")


        val textdate = findViewById<TextView>(R.id.date)
        val textanmefood = findViewById<TextView>(R.id.nameexcercise)
        val textkcal = findViewById<TextView>(R.id.Kcal)
        val textamount = findViewById<TextView>(R.id.amount)
        val texttotal = findViewById<TextView>(R.id.tatal)

        add.setOnClickListener(this)
        sub.setOnClickListener(this)


        textdate.setText(date)
        textanmefood.setText(nameExcerciseShowB)
        textkcal.setText(kcalExcerciseShowB)
        textamount.setText("${sum}")
        texttotal.setText("${resultBig}")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, list_edit_excercise::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        savelist.setOnClickListener {
            val nametype: String = "EXCERCISE"
            val nametypeStatus: String = "Edit"
            updatetodata(nameExcerciseShowB, kcalExcerciseShowB, resultBig, sum, date, idfoodShow)
            savetotalkcal(newsum, nametype, UID, statusdoting, nametypeStatus, date).savetotal()
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)

        }

    }

    private fun updatetodata(
        nameFoodShowB: String?,
        kcalfoodShowB: String?,
        resultBig: Int,
        sum: Int,
        currentDate: String,
        idfoodShow: String
    ) {

        val ref = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}")
            .child("$currentDate").child("$idfoodShow")
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("sum", sum)
        childUpdates.put("resultBig", resultBig)
        ref.updateChildren(childUpdates as Map<String, Any>)

    }

    override fun onClick(v: View?) {
        Log.d("kcalfoodShow", "${nameExcerciseShowB}")
        Log.d("kcalfoodShow", "${kcalExcerciseShowB}")
        var sumkcalsub = kcalExcerciseShowB.toInt()
        Log.d("kcalfoodShow", "${sumkcalsub}")
        var sumkcal = kcalExcerciseShowB.toInt()

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

                if (sum <= 1) {
                    sum = 1
                    resultBig = sumkcalsub
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
