package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.ImageView
import com.example.healthy_body.model.modellistfood

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*

import kotlinx.android.synthetic.main.activity_setting_user.*
import kotlinx.android.synthetic.main.list_food.view.*



class setting_user : AppCompatActivity() {


    val listset: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_user)
        listset.add("ตัเงค่าข้อมูลส่วนตว")
        listset.add("อาหาร")
        listset.add("กิจกกรม")


        // var UID: String = intent.getStringExtra("UID")
        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            // intent.putExtra("UID", UID)
            startActivity(intent)
        }

        val adapter = GroupAdapter<ViewHolder>()

        my_recycler_view.adapter = adapter

        loadfood()


    }

    private fun loadfood() {
        val adapter = GroupAdapter<ViewHolder>()
        listset.forEach {

           // val listselect = list
            val listselect = it
            Log.d("listselect", "${listselect}")
            adapter.add(settext(listselect))

            adapter.setOnItemClickListener { item, view ->
                var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
                val listsetting = item as settext
                var textlistsetting = listsetting.toString()
                val intent = Intent(this, edit_information::class.java)
                 intent.putExtra("UID", UID)
                startActivity(intent)
            }
            my_recycler_view.adapter = adapter
        }
    }

    inner class settext(val listselectt: String) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.list_setting
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {

            viewHolder.itemView.name.text = listselectt


        }
    }
}





