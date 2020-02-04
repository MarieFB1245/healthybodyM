package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.model.modellistfood
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home__user.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.barselect.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectlistfood_user : AppCompatActivity() {
   // val UID="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
    val ref = FirebaseDatabase.getInstance().getReference("FOOD")
    var UID :String=""

    var searchtext :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistfood_user)

         UID = intent.getStringExtra("UID")

        Log.e("UID =>","${UID}")
        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter

        loadfood(searchtext)

        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
             searchtext = search.text.toString()
            loadfood(searchtext)
        }




        val arrow = findViewById<ImageView>(R.id.arrow)
        val imageadd = findViewById<ImageView>(R.id.addfood)
        val imagelist = findViewById<ImageView>(R.id.addprivate)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar1)
        setSupportActionBar(tooltset)


        imagelist.setOnClickListener {
            val intent = Intent(this,selectelistfood_user_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        imageadd.setOnClickListener {
            val intent = Intent(this, addfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent) }

        }




    fun loadfood(s: String) {
        if (s != null) {
            val firebaseSrarchQuery: Query =
                ref.orderByChild("namefood").startAt(s).endAt(s + "\uf8ff")
            Log.d("firebaseSrarchQuery", "${firebaseSrarchQuery}")
            val adapter = GroupAdapter<ViewHolder>()
            firebaseSrarchQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        p0.children.forEach {
                        Log.d("text", it.toString())
                        val food = it.getValue(modellistfood::class.java)
                        if (food != null) {
                            adapter.add(Food(food))
                        }
                    }
                        adapter.setOnItemClickListener { item, view ->
                            val fooditem = item as Food
                            val intent = Intent(view.context, savedatafood_user::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("namefood", fooditem.food.namefood)
                            intent.putExtra("kcalfood", fooditem.food.kcal)
                            intent.putExtra("id", fooditem.food.id_food)
                            startActivity(intent)
                        }
                        mRecycleVeiew.adapter = adapter
                    }else{
                        SweetAlertDialog(this@selectlistfood_user, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("ไม่ค้นพบที่ค้นหา")
                            .setContentText("ต้องการเพิ่มลงในรายการหรือไม่?")
                            .setCancelText("ไม่ต้องการ!")
                            .setConfirmText("ต้องการ!")
                            .showCancelButton(true)
                            .setCancelClickListener { sDialog -> sDialog.cancel() }
                            .show()
                    }

                }

                override fun onCancelled(p0: DatabaseError) {

                }


            })
        } else {
            val adapter = GroupAdapter<ViewHolder>()
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        Log.d("text", it.toString())
                        val food = it.getValue(modellistfood::class.java)
                        Log.d("text", food.toString())
                        if (food != null) {
                            adapter.add(Food(food))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val fooditem = item as Food
                        val intent = Intent(view.context, savedatafood_user::class.java)
                        intent.putExtra("namefood", fooditem.food.namefood)
                        intent.putExtra("kcalfood", fooditem.food.kcal)
                        intent.putExtra("id", fooditem.food.id_food)
                        startActivity(intent)
                    }
                    mRecycleVeiew.adapter = adapter
                }

                override fun onCancelled(p0: DatabaseError) {

                }


            })



    }

    }
   inner class Food(val food: modellistfood) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Log.d("name_food", food.namefood)
            Log.d("kcal_String", food.kcal)
            viewHolder.itemView.name.text = food.namefood
            viewHolder.itemView.kcal.text = food.kcal
            Log.d("viewHolder", "${viewHolder}")


        }

        override fun getLayout(): Int {
            return R.layout.list_food
        }
    }


}
