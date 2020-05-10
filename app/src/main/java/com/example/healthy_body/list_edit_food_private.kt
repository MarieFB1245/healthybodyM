package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.healthy_body.model.modelSelectFood
import com.example.healthy_body.model.modelSelectFood_Private
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_list_edit_food.recyclerView
import kotlinx.android.synthetic.main.activity_list_edit_food_private.*
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.util.*

class list_edit_food_private : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var ref: DatabaseReference

    var UID:String=""
    var listcout :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_edit_food_private)

        UID = intent.getStringExtra("UID")
        val adapter = GroupAdapter<ViewHolder>()
        recyclerView.adapter = adapter


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            val callbackAc ="1"
            intent.putExtra("callbackAc",callbackAc)
            startActivity(intent)
            finish()
        }
        loaddata()


        addfood.setOnClickListener {
            val intent = Intent(this, addfood_user_private_Bsetting::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }
    }



    private fun loaddata() {

        val adapter = GroupAdapter<ViewHolder>()
        ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${UID}")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("p0", p0.toString())
                if(p0.exists()){
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(modelSelectFood_Private::class.java)
                        Log.d("text", listfood.toString())
                        if (listfood != null) {
                            adapter.add(Foodprivate(listfood))
                        }

                        listcout= listcout + 1
                        list_num_food.setText(listcout.toString())

                    }
                    adapter.setOnItemClickListener { item, view ->
                        val itemf = item as Foodprivate
                        Log.e("fooditem","${itemf}")
                        val intent = Intent(view.context, list_saveedit_food_menu_private::class.java)
                        intent.putExtra("UID",UID)
                        intent.putExtra("id_food", itemf.food.id_food)
                        intent.putExtra("namefood", itemf.food.namefood)
                        intent.putExtra("amount", itemf.food.amount)
                        intent.putExtra("unit", itemf.food.unit)
                        intent.putExtra("unittype", itemf.food.unittype)
                        intent.putExtra("kcal", itemf.food.kcal)
                        startActivity(intent)
                        finish()
                    }
                    recyclerView.adapter = adapter
                }else{
                    show()
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
        val callbackAc ="1"
        intent.putExtra("callbackAc",callbackAc)
        startActivity(intent)
        finish()

    }
    inner class Foodprivate(val food: modelSelectFood_Private) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= food.namefood
            viewHolder.itemView.kcal.text = food.kcal.toString()
            Log.d("viewHolder", "${viewHolder}")
        }

        override fun getLayout(): Int {
            return R.layout.listselect_edit_food_private

        }
    }
    private fun show(){
        Toast.makeText(this, "ไม่มีรายการอาหารที่เลือกไว้", Toast.LENGTH_LONG).show()
    }
}
