package com.example.healthy_body

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import com.example.healthy_body.calculate.data
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


    private lateinit var ref: DatabaseReference
   // val UID="mPJPyPSJDiXCDKBDQpn5QinolgW2"
    var calendar = Calendar.getInstance()
    var UID :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_edit_food)

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textcalendar!!.text = sdf.format(calendar.getTime())
        //val date  = sdf.format(calendar.getTime())
        var date :String = "22-1-2020"
        Log.d("text", date.toString())
        val adapter = GroupAdapter<ViewHolder>()
        recyclerView.adapter = adapter

       // UID = intent.getStringExtra("UID")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        loaddata(date)




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
                    dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()

            }


        })

    }

    private fun loaddata(date :String) {
        val adapter = GroupAdapter<ViewHolder>()
        ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("text", p0.toString())
                if(p0.exists()){
                    p0.children.forEach {
                        Log.d("text", it.toString())
                        val listfood = it.getValue()
                        Log.d("text", listfood.toString())
                        if (listfood != null) {
                           // adapter.add(Food(listfood))
                        }
                    }
                    recyclerView.adapter = adapter
                }else{
                    show()
                }

            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }


    inner class Food(val food: dataselectfood) : Item<ViewHolder>() {
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
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textcalendar!!.text = sdf.format(calendar.getTime())
        Log.e("carenda","${sdf.format(calendar.getTime())}")
    }

    private fun show(){
        Toast.makeText(this, "ไม่มีรายการอาหารที่เลือกไว้", Toast.LENGTH_LONG).show()
    }
}
class dataselectfood (val nameFoodShowB: String,val kcalfoodShowB: String,val resultBig: Int,val sum: Int,val date :String,id:String){
    constructor():this("","",0,0,"","")

}
