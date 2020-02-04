package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.healthy_body.R.drawable.color_chart
import com.example.healthy_body.calculate.data
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.example.healthy_body.calculate.totalkcallare
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home__user.*
import java.util.ArrayList

class Home_User : AppCompatActivity() {


    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()
    val UID="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__user)

        myRef = FirebaseDatabase.getInstance().reference
      // var UID: String = intent.getStringExtra("UID")
        val TDEEshow = findViewById<TextView>(R.id.numberTDEE)
        val BMIshow = findViewById<TextView>(R.id.numberBMI)
        val BMRshow = findViewById<TextView>(R.id.numberBMR)


        signout.setOnClickListener {
            signout()
        }

        selectdata_totalkcal(UID).getdatatotal{excercise,food ->

           var Food = food.toInt()
           var  Workout = excercise.toInt()

            setupPieChart(Food,Workout)
}
        val progest  = ProgressDialog(this,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()

        Log.e("pass","selectdata_totalkcal")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>?
                val map1 = dataSnapshot.child("users").child(UID).value as Map<*, *>?
                val BMI = map1!!["bmis"].toString()
                val BMR = map1!!["BMR"].toString()
                val TDEE = map1["TDEE"].toString()
                if(BMI !=""){
                    progest.cancel()
                    TDEEshow.text = TDEE
                    BMIshow.text = BMI
                    BMRshow.text = BMR
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        bottonop.setOnClickListener {
            val intent = Intent(this,setting_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)

        }
        addworkout.setOnClickListener {
            val intent = Intent(this,selectlistexcercise_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)

        }

        addfood.setOnClickListener{
            val intent = Intent(this,selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }
    }

    private fun setupPieChart(Food:Int ,Workout:Int) {

         val peercenData = intArrayOf(Food, Workout)
         val pnameFood = arrayOf("อาหาร", "กิจกกรม")

        val pieEntries = ArrayList<PieEntry>()
        for (i in peercenData.indices) {
            pieEntries.add(
                PieEntry(
                    peercenData[i].toFloat(),
                    pnameFood[i]
                )
            )//ค่าที่เก็บจะต้องเป็น array
        }


        val dataSet = PieDataSet(pieEntries, "KCAL")
        val colors = ArrayList<Int>()
        colors.add(Color.rgb(171,69,204))
        colors.add(Color.rgb(51,102,255))
        dataSet.colors = colors
        val data = PieData(dataSet)
        val chart = findViewById(R.id.chart) as PieChart
        chart.getDescription().setEnabled(false)
        chart.getLegend().setEnabled(false)
        chart.data = data
        chart.isDrawHoleEnabled = false
        chart.centerTextRadiusPercent
        chart.setCenterTextSizePixels(500f)
        data.setValueTextSize(30f)
        chart.animateY(500)
        chart.invalidate()


    }
    private fun signout (){
        val intent = Intent(this, Login_user::class.java)
        myAut.signOut()
        startActivity(intent)
    }
}
