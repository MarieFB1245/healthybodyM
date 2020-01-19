package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.healthy_body.model.modellistfood
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home__user.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectlistfood_user : AppCompatActivity() {
   // var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    val ref = FirebaseDatabase.getInstance().getReference("FOOD")
var UID :String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistfood_user)

         UID = intent.getStringExtra("UID")
        Log.e("UID =>","${UID}")
        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter
        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
            var searchtext = search.text.toString()
            loadfood(searchtext)
        }




        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar1)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.addfood) as ImageView
        imageView.setOnClickListener {
            val intent = Intent(this, addfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }



    }

     fun loadfood(s: String) {
        if (s != null) {
            val firebaseSrarchQuery: Query =
                ref.orderByChild("name_food").startAt(s).endAt(s + "\uf8ff")
            Log.d("firebaseSrarchQuery", "${firebaseSrarchQuery}")
            val adapter = GroupAdapter<ViewHolder>()
            firebaseSrarchQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
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
                        intent.putExtra("namefood", fooditem.food.name_food)
                        intent.putExtra("kcalfood", fooditem.food.kcal)
                        intent.putExtra("id", fooditem.food.id_food)
                        startActivity(intent)
                    }
                    mRecycleVeiew.adapter = adapter
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
                        if (food != null) {
                            adapter.add(Food(food))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val fooditem = item as Food
                        val intent = Intent(view.context, savedatafood_user::class.java)
                        intent.putExtra("namefood", fooditem.food.name_food)
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
            Log.d("name_food", food.name_food)
            Log.d("kcal_String", food.kcal)
            viewHolder.itemView.name.text = food.name_food
            viewHolder.itemView.kcal.text = food.kcal
            Log.d("viewHolder", "${viewHolder}")


        }

        override fun getLayout(): Int {
            return R.layout.list_food
        }
    }


}
