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
import com.example.healthy_body.model.modelSelectExcercise_Private
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_edit_excercise_private.*
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_list_edit_food.recyclerView
import kotlinx.android.synthetic.main.activity_list_edit_food_private.*
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.text.SimpleDateFormat
import java.util.*

class list_edit_excercise_private : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    //val UID ="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    var calendar = Calendar.getInstance()
    var UID :String=""
    var KEY :String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_edit_excercise_private)

        UID = intent.getStringExtra("UID")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, setting_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        loaddata()


        addexcercise.setOnClickListener {
            val intent = Intent(this, addexcercise_user_private_Bsetting::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }


    }
    private fun loaddata() {

        val adapter = GroupAdapter<ViewHolder>()
        ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${UID}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("p0", p0.toString())
                if(p0.exists()){
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(modelSelectExcercise_Private::class.java)
                        Log.d("text", KEY)
                        if (listfood != null) {
                            adapter.add(Foodd(listfood))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val itemf = item as Foodd
                        Log.e("fooditem","${itemf}")
                        val intent = Intent(view.context, list_saveedit_excercise_menu_private::class.java)
                        intent.putExtra("id_excercise",itemf.excercise.id_excercise)
                        intent.putExtra("UID",UID)
                        intent.putExtra("name_excercise", itemf.excercise.name_excercise)
                        intent.putExtra("kcal", itemf.excercise.kcal)
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                }else{
                    show()
                }

            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }
    inner class Foodd(val excercise: modelSelectExcercise_Private) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= excercise.name_excercise
            viewHolder.itemView.kcal.text = excercise.kcal
            Log.d("viewHolder", "${viewHolder}")
        }

        override fun getLayout(): Int {
            return R.layout.listselect_edit_food

        }
    }


    private fun show(){
        Toast.makeText(this, "ไม่มีรายการอาหารที่เลือกไว้", Toast.LENGTH_LONG).show()
    }
}
