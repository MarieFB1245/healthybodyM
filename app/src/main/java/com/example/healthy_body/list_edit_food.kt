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
import com.example.healthy_body.calculate.data
import com.example.healthy_body.calculate.dateselect_totalvalue
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.example.healthy_body.model.modellistfood
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.text.SimpleDateFormat
import java.util.*

class list_edit_food : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    private lateinit var ref: DatabaseReference

    var calendar = Calendar.getInstance()
    var UID :String=""
    var adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_edit_food)

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

            var Food = food.toInt()
            var  Workout = excercise.toInt()

            numbertotalkcal.setText(food)
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
                DatePickerDialog(this@list_edit_food,
                    R.style.DialogTheme,
                    dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()


            }


        })

    }

    private fun loaddata(date :String) {
        val progest  = ProgressDialog(this,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()

        Log.e("date","${date}")

        recyclerView?.adapter?.notifyDataSetChanged()
        adapter.clear()
        ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child(date)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("p0", p0.toString())
                recyclerView?.adapter?.notifyDataSetChanged()
                adapter.clear()
                if(p0.exists()){
                    adapter.clear()
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(dataselectfood::class.java)
                        Log.d("text", listfood.toString())
                        if (listfood != null) {
                            adapter.add(Foodd(listfood))

                        }
                        progest.cancel()
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val itemf = item as Foodd
                        Log.e("fooditem","${itemf}")
                        val intent = Intent(view.context, list_saveedit_food::class.java)
                        intent.putExtra("UID",UID)
                        intent.putExtra("key", itemf.food.id_list)
                        intent.putExtra("date", itemf.food.date)
                        intent.putExtra("nameFoodShowB", itemf.food.nameFoodShowB)
                        intent.putExtra("resultBig", itemf.food.resultBig)
                        intent.putExtra("sum", itemf.food.sum)
                        intent.putExtra("id", itemf.food.id)
                        intent.putExtra("kcalfoodShowB", itemf.food.kcalfoodShowB)
                        startActivity(intent)
                        finish()
                    }
                    recyclerView.adapter = adapter
                }else{
                    Log.e("error","don have")
                    progest.cancel()
                    adapter.clear()
                    show()
                }
            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }


    inner class Foodd(var food: dataselectfood) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= food.nameFoodShowB
            viewHolder.itemView.kcal.text = food.kcalfoodShowB
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

        //ส่งค่า UID and date  ไป เเล้ว callback กลับมา food excersice

        dateselect_totalvalue(UID,data).callvalue{excercise,food ->
            numbertotalkcal.setText(food)
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
class dataselectfood (val id_list :String, val nameFoodShowB: String,val kcalfoodShowB: String,val resultBig: Int,val sum: Int,val date :String,val id:String){
    constructor():this("","","",0,0,"","")

}
