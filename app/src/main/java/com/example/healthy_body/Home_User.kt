package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.R.drawable.color_chart
import com.example.healthy_body.calculate.data
import com.example.healthy_body.calculate.delectdata_private_excercise
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
import kotlinx.android.synthetic.main.fragment_home_.*
import java.util.ArrayList

class Home_User : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var myRef: DatabaseReference
    private var myAut = FirebaseAuth.getInstance()
   // val UID="BmmUcmBYN2MmvJr1oFt3MQkKDCU2"
  // val UID="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__user)

       var UID: String = intent.getStringExtra("UID")


if(intent.getStringExtra("callbackAc")==null){
if(intent.getStringExtra("backtohome")==null){
    val textFragment = HOME_Fragment()
    val bundle = Bundle()
    bundle.putString("UID",UID)
    textFragment.setArguments(bundle)
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()
    bottom_navigation.menu.getItem(0).isEnabled = false

}else{
    var backtohome: String = intent.getStringExtra("backtohome")
    val textFragment = HOME_Fragment()
    val bundle = Bundle()
    bundle.putString("UID",UID)
    bundle.putString("backtohome",backtohome)
    textFragment.setArguments(bundle)
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()
    bottom_navigation.menu.getItem(0).isEnabled = false
}



}else{
    val textFragment = SETTING_Fragment()
    val bundle = Bundle()
    bundle.putString("UID",UID)
    textFragment.setArguments(bundle)
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()

    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()
    bottom_navigation.menu.getItem(1).isEnabled = false
    bottom_navigation.menu.getItem(1).isChecked = true
}




        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.item1 ->{
                    home_user.setBackgroundResource(R.drawable.backgroud_status )
                    bottom_navigation.menu.getItem(0).isEnabled = false
                    bottom_navigation.menu.getItem(1).isEnabled = true
                    val textFragment = HOME_Fragment()
                    val bundle = Bundle()
                    bundle.putString("UID",UID)
                    textFragment.setArguments(bundle)
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
                    transaction.replace(R.id.fragment_container,textFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                    return@setOnNavigationItemSelectedListener true

                }R.id.item2 -> {
                home_user.setBackgroundResource(R.drawable.backgroud_status )
                bottom_navigation.menu.getItem(1).isEnabled = false
                bottom_navigation.menu.getItem(0).isEnabled = true
               val textFragment = SETTING_Fragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                val bundle = Bundle()
                bundle.putString("UID",UID)
                textFragment.setArguments(bundle)
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.fragment_container,textFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                return@setOnNavigationItemSelectedListener true
            }else ->{
                false
            }


            }


        }


        /* myRef = FirebaseDatabase.getInstance().reference
         var UID: String = intent.getStringExtra("UID")
         val TDEEshow = findViewById<TextView>(R.id.numberTDEE)
         val BMIshow = findViewById<TextView>(R.id.numberBMI)
         val BMRshow = findViewById<TextView>(R.id.numberBMR
         )

         signout.setOnClickListener {
             signout()
             finish()
         }

         buttonRe.setOnClickListener {
             val intent = Intent(this,dashboard_user::class.java)
             intent.putExtra("UID",UID)
             startActivity(intent)
             finish()
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
             finish()
         }
         addworkout.setOnClickListener {
             val intent = Intent(this,selectlistexcercise_user::class.java)
             intent.putExtra("UID",UID)
             startActivity(intent)
             finish()
         }

         addfood.setOnClickListener{
             val intent = Intent(this,selectlistfood_user::class.java)
             intent.putExtra("UID",UID)
             startActivity(intent)
             finish()
         }*/



    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("คุณเเน่ใจ?")
            .setContentText("ว่าต้องการออกจากระบบ!")
            .setCancelText("ไม่ต้องการ!")
            .setConfirmText("ต้องการ!")
            .showCancelButton(true)
            .setCancelClickListener { sDialog -> sDialog.cancel()
                this.doubleBackToExitPressedOnce = false
            }
            .setConfirmClickListener {


                val intent = Intent(this, Login_user::class.java)
                myAut.signOut()
                this.finish()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
            .show()


    }

/*
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


    }*/

}
