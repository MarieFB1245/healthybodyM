package com.example.healthy_body

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.model.GradientColor
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard_user.*

class dashboard_user : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    var UID :String="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)



       // UID = intent.getStringExtra("UID")

        ref = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${UID}")
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

                        Log.e("group1","${group1}")
                        Log.e("group2","${group2}")


                        Log.e("j","${j}")
                        totalfood.add(BarEntry(j.toFloat(), group1.toFloat()))
                        totalexcercise.add(BarEntry(j.toFloat(), group2))



                        var bardataset = BarDataSet(totalfood,"TOTALFOOD")
                        bardataset.setColors(Color.rgb(171,69,204))
                        bardataset.setValueTextColor(Color.WHITE)

                        var bardatasets = BarDataSet(totalexcercise,"TOTALEXCERCISE")
                        bardatasets.setColors(Color.rgb(51,102,255))
                        bardatasets.setValueTextColor(Color.WHITE)




                        day.add("${listfood!!.DATE}")
                        val xAxis = barChartView.getXAxis()
                        xAxis.setValueFormatter(IndexAxisValueFormatter(day))
                        xAxis.setDrawAxisLine(true)
                        xAxis.setDrawGridLines(false)
                        xAxis.setCenterAxisLabels(true)
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
                        xAxis.setGranularity(1f)
                        xAxis.setGranularityEnabled(true)
                        barChartView.setDragEnabled(true)
                        barChartView.setVisibleXRangeMaximum(5f)

                        var bardata = BarData(bardatasets,bardataset)
                        barChartView.setData(bardata)

                        barChartView.getXAxis().setTextColor(Color.WHITE);
                        val yAxis = barChartView.getAxisLeft()
                        yAxis.setDrawGridLines(false)

                        val barSpace = 0.05f
                        val groupspace = 0.30f
                        bardata.setBarWidth(0.30f)




                        barChartView.getXAxis().setAxisMinimum(0f)
                        barChartView.getXAxis().setAxisMaximum(0+barChartView.getBarData().getGroupWidth(groupspace,barSpace)*j)
                        barChartView.getAxisLeft().setAxisMinimum(0f)
                        barChartView.animateY(500)

                        barChartView.setExtraOffsets(0f,0f,0f,10f)
                        barChartView.groupBars(0f,groupspace,barSpace)
                        barChartView.description.setEnabled(false)
                        barChartView.invalidate()
barChartView.setBackgroundResource(R.drawable.all_background)


                        barChartView.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
                        barChartView.getAxisRight().setTextColor(Color.WHITE)
                        barChartView.getXAxis().setTextColor(Color.WHITE);
                        barChartView.getLegend().setTextColor(Color.WHITE);
                        seetdata(bardataset,bardatasets)
                        j=j+1
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
}

data class totalkcallare (var TOTALEXCERCISE:Int=0 ,var TOTALFOOD: Int=0 ,var DATE :String="")