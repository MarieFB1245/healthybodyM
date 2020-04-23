package com.example.healthy_body

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.model.GradientColor
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard_user.*
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_totalkcal__user.*
import java.text.Collator
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.github.mikephil.charting.components.LimitLine
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.healthy_body.model.User


class dashboard_user : AppCompatActivity() {
     lateinit var ref: DatabaseReference
    private var doubleBackToExitPressedOnce = false
   var UID :String="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
   //var UID :String=""
    var calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)
        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val datetext  = sdf.format(calendar.getTime())


//        UID = intent.getStringExtra("UID")


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }
        ref = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${UID}")


        //เเก้เเล้ว filter


      //  val currentDater = sdf.format(Date())
      //  val date = SimpleDateFormat("dd-M-yyyy").parse("$currentDater")
      //  val testtime = date.time
      //  val tests = testtime.toString()

           // .orderByChild("TimeStamp").startAt("1575133200000")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var j=0

                    val totalfood = ArrayList<BarEntry>()
                    val totalexcercise = ArrayList<BarEntry>()
                    var day = ArrayList<String>()
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())

                        var listfood = it.getValue(totalkcallare::class.java)




                        var group1= listfood!!.TOTALFOOD
                        var group2 = listfood!!.TOTALEXCERCISE.toFloat()



                        totalfood.add(BarEntry(j.toFloat(), group1.toFloat()))
                        totalexcercise.add(BarEntry(j.toFloat(), group2))




                        var bardataset = BarDataSet(totalfood,"TOTALFOOD")
                        bardataset.setColors(Color.rgb(171,69,204))
                        bardataset.setValueTextColor(Color.WHITE)


                        var bardatasets = BarDataSet(totalexcercise,"TOTALEXCERCISE")
                        bardatasets.setColors(Color.rgb(51,102,255))
                        bardatasets.setValueTextColor(Color.WHITE)




                        day.add("${listfood!!.DATE}")

                        Log.e("totalfood","${totalfood}")
                        Log.e("group1","${group1}")



                        Log.e("day","$day")

                        var xAxis = barChartView.getXAxis()

                        xAxis.setValueFormatter(IndexAxisValueFormatter(day))
                        xAxis.setAvoidFirstLastClipping(true)

                        xAxis.setDrawAxisLine(true)
                        xAxis.setDrawGridLines(false)
                        xAxis.setCenterAxisLabels(true)
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
                        xAxis.setGranularity(1f)
                        xAxis.setGranularityEnabled(true)
                        barChartView.setDragEnabled(true)
                        barChartView.setVisibleXRangeMaximum(5f)

                        var bardata = BarData(bardatasets,bardataset)

                        var ref: DatabaseReference
                        ref = FirebaseDatabase.getInstance().getReference("users").child("${UID}")
                        ref.addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {

                                Log.e("p0","${p0}")

                              val textTDEE = p0.getValue(Userlimit::class.java)
                                var maxCapacity = textTDEE!!.TDEE
                                val ll = LimitLine(maxCapacity!!.toFloat(), "TDEE")
                                ll.setTextColor(Color.WHITE)
                                barChartView.getAxisLeft().addLimitLine(ll)
                            }

                        })
                        barChartView.setData(bardata)








                        barChartView.getXAxis().setTextColor(Color.WHITE)
                        val yAxis = barChartView.getAxisLeft()
                        yAxis.setDrawGridLines(false)

                        val barSpace = 0.05f
                        val groupspace = 0.30f
                        bardata.setBarWidth(0.30f)



                        barChartView.getXAxis().setAxisMinimum(0f)
                        barChartView.getXAxis().setAxisMaximum(0+barChartView.getBarData().getGroupWidth(groupspace,barSpace)*j+1)
                        barChartView.getAxisLeft().setAxisMinimum(0f)
                        barChartView.animateY(500)

                        barChartView.setExtraOffsets(0f,0f,0f,10f)
                        barChartView.groupBars(0f,groupspace,barSpace)
                        barChartView.description.setEnabled(false)

                        barChartView.setBackgroundResource(R.drawable.all_background)


                        barChartView.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
                        barChartView.getAxisRight().setTextColor(Color.WHITE)
                        barChartView.getXAxis().setTextColor(Color.WHITE);
                        barChartView.getLegend().setTextColor(Color.WHITE);

                        seetdata(bardataset,bardatasets)
                        j=j+1
                        Log.e("j","$j")
                        barChartView.invalidate()
                    }






                }else{
                    Log.e("error","don have")
                }
            }

            private fun seetdata(bardataset: BarDataSet,bardatasets:BarDataSet) {
                Log.e("bardataset","${bardataset}")
                Log.e("bardatasets","${bardatasets}")




            }

            override fun onCancelled(p0: DatabaseError) {}
        })



    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, Home_User::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()

    }
}
//var TimeStamp:String =""    /* Model */
data class totalkcallare (var TOTALEXCERCISE:Int=0 ,var TOTALFOOD: Int=0 ,var DATE :String="")



data class Userlimit(val textfirstname :String ="", val textlastname:String="", val age :Float?= null , val weigth:Float?=null, val height:Float?=null,val gender :String = "",val level_Workout :String="" ,val BMR:Int=0
,val BMIS:String ="",val status:String="",val uid:String="",val email:String="",val password:String="",val TDEE :Long?=null)