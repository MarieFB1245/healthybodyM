package com.example.healthy_body

import android.app.DatePickerDialog
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
import androidx.core.view.isVisible
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.model.User
import kotlinx.android.synthetic.main.app_dashboard.*


class dashboard_user : AppCompatActivity() {
    var monthYearStr: String=""

    internal var sdf = SimpleDateFormat("M-yyyy")
    internal var input = SimpleDateFormat("yyyy-MM-dd")


    lateinit var ref: DatabaseReference
    private var doubleBackToExitPressedOnce = false
  // var UID :String="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
   var UID :String=""
    var calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)
        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val datetext  = sdf.format(calendar.getTime())


       UID = intent.getStringExtra("UID")

        val yeartext = calendar.get(Calendar.YEAR)
        val monthtext = calendar.get(Calendar.MONTH)+1


        Log.e("textdayfirst","$monthtext")

        if(monthtext == 1||monthtext == 3||monthtext == 5||monthtext == 7||monthtext == 8||monthtext == 10||monthtext == 12){

            val textdayfirst =  "1"+"-"+monthtext.toString()+"-"+yeartext.toString()
            val textdaylast =  "31"+"-"+monthtext.toString()+"-"+yeartext.toString()
            Log.e("textdayfirst","$textdayfirst")
            Log.e("textdayfirst","$textdaylast")

            setdate(textdayfirst ,textdaylast)

        }else if (monthtext == 2){

            val textdayfirst =  "1"+"-"+monthtext.toString()+"-"+yeartext.toString()
            val textdaylast =  "29"+"-"+monthtext.toString()+"-"+yeartext.toString()
            Log.e("textdayfirst","$textdayfirst")
            Log.e("textdayfirst","$textdaylast")

            setdate(textdayfirst ,textdaylast)



        }else{

            val textdayfirst =  "1"+"-"+monthtext.toString()+"-"+yeartext.toString()
            val textdaylast =  "30"+"-"+monthtext.toString()+"-"+yeartext.toString()
            Log.e("textdayfirst","$textdayfirst")
            Log.e("textdayfirst","$textdaylast")

            setdate(textdayfirst ,textdaylast)


        }

        /*   val currentDater = sdf.format(Date())
           val date = SimpleDateFormat("dd-M-yyyy").parse("$currentDater")
           val testtime = date.time
           val tests = testtime.toString()
   */




        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }

        monthyaerselect.setOnClickListener {
            val pickerDialog = datepickerdialog()
            pickerDialog.setListener(DatePickerDialog.OnDateSetListener { datePicker, year, month, i2 ->
                monthYearStr = year.toString() + "-" + (month + 1) + "-" + i2

                val year = year
                val month =  month

                if(month == 1 ||month == 3 ||month == 5 ||month == 7 ||month == 8 ||month == 10 ||month == 12){
                    val textdayfirst =  "1"+"-"+month.toString()+"-"+year.toString()
                    val textdaylast =  "31"+"-"+month.toString()+"-"+year.toString()
                    Log.e("textdayfirst","$textdayfirst")
                    Log.e("textdayfirst","$textdaylast")

                    setdate(textdayfirst ,textdaylast)
                }else if (month == 2){
                    val textdayfirst =  "1"+"-"+month.toString()+"-"+year.toString()
                    val textdaylast =  "29"+"-"+month.toString()+"-"+year.toString()
                    Log.e("textdayfirst","$textdayfirst")
                    Log.e("textdayfirst","$textdaylast")

                    setdate(textdayfirst ,textdaylast)
                }else{

                    val textdayfirst =  "1"+"-"+month.toString()+"-"+year.toString()
                    val textdaylast =  "30"+"-"+month.toString()+"-"+year.toString()
                    Log.e("textdayfirst","$textdayfirst")
                    Log.e("textdayfirst","$textdaylast")

                    setdate(textdayfirst ,textdaylast)

                }

            })
            pickerDialog.show(supportFragmentManager, "MonthYearPickerDialog")
        }






    }

    private fun setdate(textdayfirst: String="", textdaylast: String="") {

        var datefrist = SimpleDateFormat("dd-M-yyyy").parse("$textdayfirst")
        var testtimefrist = datefrist.time
        var testsfrist = testtimefrist.toString()
        Log.e("textdayfirst","$testsfrist")
        var datelast = SimpleDateFormat("dd-M-yyyy").parse("$textdaylast")
        var testtimelast = datelast.time
        var testslast = testtimelast.toString()
        Log.e("textdayfirst","$testslast")

        loaddatachar(testsfrist,testslast)


    }

    private fun loaddatachar(testsfrist: String="", testslast: String="") {



        ref = FirebaseDatabase.getInstance().getReference("TOTALKCAL").child("${UID}")
        ref.orderByChild("TimeStamp").startAt(testsfrist).endAt(testslast).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("DataSnapshot", p0.toString())
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
                        xAxis.setLabelRotationAngle(-30f);
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
                                var lowCapacity = textTDEE!!.BMR
                                val ll = LimitLine(maxCapacity!!.toFloat(), "TDEE")
                                val l2 = LimitLine(lowCapacity!!.toFloat(), "BMR")
                                ll.setTextColor(Color.BLACK)
                                l2.setTextColor(Color.BLACK)
                                barChartView.getAxisLeft().addLimitLine(ll)
                                barChartView.getAxisLeft().addLimitLine(l2)
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

                        barChartView.setBackgroundResource(R.color.myPeach)


                        barChartView.getAxisLeft().setTextColor(Color.BLACK); // left y-axis
                        barChartView.getAxisRight().setTextColor(Color.BLACK)
                        barChartView.getXAxis().setTextColor(Color.BLACK);
                        barChartView.getLegend().setTextColor(Color.BLACK);

                        j=j+1
                        Log.e("j","$j")
                        barChartView.isVisible=true
                        barChartView.invalidate()


                    }






                }else{
                    barChartView.isVisible=false
                    SweetAlertDialog(this@dashboard_user, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ไม่พบข้อมูล!")
                        .setContentText("กรุณากรอกข้อมูลให้ถูกต้อง")
                        .setConfirmText("ตกลง")
                        .showCancelButton(false)
                        .setCancelClickListener { sDialog -> sDialog.cancel()
                        }
                        .show()
                }
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
//  /* Model */
data class totalkcallare (var TOTALEXCERCISE:Int=0 ,var TOTALFOOD: Int=0 ,var DATE :String="",var TimeStamp:String =""  )



data class Userlimit(val textfirstname :String ="", val textlastname:String="", val age :Float?= null , val weigth:Float?=null, val height:Float?=null,val gender :String = "",val level_Workout :String="" ,val BMR:Int=0
,val BMIS:String ="",val status:String="",val uid:String="",val email:String="",val password:String="",val TDEE :Long?=null)