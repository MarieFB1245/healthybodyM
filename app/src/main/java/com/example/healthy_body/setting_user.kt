package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter

import kotlinx.android.synthetic.main.activity_setting_user.*
import java.security.acl.Group


class setting_user : AppCompatActivity() {

   private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_user)


       // val adapter =GroupAdapter<RecyclerView.ViewHolder>()


        my_recycler_view.adapter




        var UID: String = intent.getStringExtra("UID")
        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

    }
}