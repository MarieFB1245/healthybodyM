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

    private val Food = 0
    private val Workout = 0
    private val peercenData = intArrayOf(Food, Workout)
    private val pnameFood = arrayOf("Kcal/Food", "Kcal/Workout")
    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()
   // val UID="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__user)
        setupPieChart()
        myRef = FirebaseDatabase.getInstance().reference
        var UID: String = intent.getStringExtra("UID")
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
            val intent = Intent(this,edit_information::class.java)
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
