package com.example.healthy_body

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import com.example.healthy_body.calculate.dateselect_totalvalue
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.text.SimpleDateFormat
import java.util.*

class list_edit_excercise : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var ref: DatabaseReference
    //val UID ="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    var calendar = Calendar.getInstance()
    var UID :String=""
var KEY :String=""
    var adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_edit_excercise)


        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textcalendar!!.text = sdf.format(calendar.getTime())
        val datetext  = sdf.format(calendar.getTime())

        val adapter = GroupAdapter<ViewHolder>()
        recyclerView.adapter = adapter

         UID = intent.getStringExtra("UID")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)


        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }

        loaddata(datetext)



        selectdata_totalkcal(UID).getdatatotal{ excercise, food ->
            numbertotalkcal.setText(excercise)
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        textcalendar!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                DatePickerDialog(this@list_edit_excercise,
                    R.style.DialogTheme,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()


            }



        })

    }
    private fun loaddata(date :String) {
        Log.e("date","${date}")
        val progest  = ProgressDialog(this,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()
        adapter.clear()
        ref = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID}").child(date)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("p0", p0.toString())
                if(p0.exists()){
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(dataselectexcercise::class.java)
                        Log.d("text", KEY)
                        if (listfood != null) {
                            adapter.add(Foodd(listfood))
                        }
                        progest.cancel()
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val itemf = item as Foodd
                        Log.e("fooditem","${itemf}")
                        val intent = Intent(view.context, list_saveedit_excercise::class.java)
                        intent.putExtra("key",itemf.excercise.id_list)
                        intent.putExtra("UID",UID)
                        intent.putExtra("date", itemf.excercise.date)
                        intent.putExtra("nameExcerciseShowB", itemf.excercise.nameExcerciseShowB)
                        intent.putExtra("resultBig", itemf.excercise.resultBig)
                        intent.putExtra("sum", itemf.excercise.sum)
                        intent.putExtra("id", itemf.excercise.id)
                        intent.putExtra("kcalExcerciseShowB", itemf.excercise.kcalExcerciseShowB)
                        startActivity(intent)
                        finish()
                    }
                    recyclerView.adapter = adapter
                }else{
                    adapter.clear()
                    progest.cancel()

                    show()
                }

            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }
    inner class Foodd(val excercise: dataselectexcercise) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= excercise.nameExcerciseShowB
            viewHolder.itemView.kcal.text = excercise.kcalExcerciseShowB
            Log.d("viewHolder", "${viewHolder}")
        }

        override fun getLayout(): Int {
            return R.layout.listselect_edit_food

        }
    }

    private fun updateDateInView() {
        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textcalendar!!.text = sdf.format(calendar.getTime())
        val data = sdf.format(calendar.getTime())
        loaddata(data)
        dateselect_totalvalue(UID,data).callvalue{ excercise, food ->
            numbertotalkcal.setText(excercise)
        }

    }

    private fun show(){
        Toast.makeText(this, "ไม่มีรายการอาหารที่เลือกไว้", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, setting_user::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()

    }
}
class dataselectexcercise (val id_list :String, val nameExcerciseShowB: String,val kcalExcerciseShowB: String,val resultBig: Int,val sum: Int,val date :String,val id:String){
    constructor():this("","","",0,0,"","")

}