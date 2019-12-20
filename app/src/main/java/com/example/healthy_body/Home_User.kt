package com.example.healthy_body

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home__user.*
import java.util.ArrayList

class Home_User : AppCompatActivity() {

    private val test = 50
    private val test2 = 50
    private val peercenData = intArrayOf(test, test2)
    private val pnameFood = arrayOf("Food", "Workout")
    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__user)
        setupPieChart()
        myRef = FirebaseDatabase.getInstance().reference
        var UID: String = intent.getStringExtra("uid")
        val TDEEshow = findViewById<TextView>(R.id.numberTDEE)
        val BMIshow = findViewById<TextView>(R.id.numberBMI)
        val BMRshow = findViewById<TextView>(R.id.numberBMR)

        signout.setOnClickListener {
            signout()
        }
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>?
                val map1 = dataSnapshot.child("users").child(UID).value as Map<*, *>?
                val BMI = map1!!["bmis"].toString()
                val BMR = map1!!["BMR"].toString()
                val TDEE = map1["TDEE"].toString()
                TDEEshow.text = TDEE
                BMIshow.text = BMI
                BMRshow.text = BMR
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        bottonop.setOnClickListener {
            val intent = Intent(this, setting_user::class.java)
            intent.putExtra("uid",UID)
            startActivity(intent)

        }
    }

    private fun setupPieChart() {
        val pieEntries = ArrayList<PieEntry>()
        for (i in peercenData.indices) {
            pieEntries.add(
                PieEntry(
                    peercenData[i].toFloat(),
                    pnameFood[i]
                )
            )//ค่าที่เก็บจะต้องเป็น array
        }
        val dataSet = PieDataSet(pieEntries, "test")
        val colors = ArrayList<Int>()

        colors.add(Color.RED)
        colors.add(Color.BLUE)
        dataSet.colors = colors
        val data = PieData(dataSet)
        val chart = findViewById(R.id.chart) as PieChart
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.data = data
        chart.isDrawHoleEnabled = false
        chart.setCenterTextSizePixels(500f)
        data.setValueTextSize(30f);
        chart.animateY(500)
        chart.invalidate()


    }
    private fun signout (){
        val intent = Intent(this, Login_user::class.java)
        myAut.signOut()
        startActivity(intent)
    }
}
