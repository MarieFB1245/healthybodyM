package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import com.example.healthy_body.model.modellistfood
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectelistfood_user_private : AppCompatActivity() {

    //var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    private var doubleBackToExitPressedOnce = false
    var UID :String=""
    var searchtext :String=""
var backtohome: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectelistfood_user_private)

        UID = intent.getStringExtra("UID")
        if(intent.getStringExtra("backtohome")!=null) backtohome = intent.getStringExtra("backtohome")

        if (intent.getStringExtra("nametypeStatus") != null){
            baranimation.isVisible =true
            val slide_down = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
            slide_down.setFillAfter(true)
            baranimation.startAnimation(slide_down);

            Handler().postDelayed({
                val slide_up = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up)
                baranimation.startAnimation(slide_up);
                baranimation.isVisible = false
            }, 3000)
        }



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
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar1)
        setSupportActionBar(tooltset)


        arrow.setOnClickListener {

            if(backtohome !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, selectlistfood_user::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("back_home_add",backtohome)
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, selectlistfood_user::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }


        }

        imageadd.setOnClickListener {
            if(backtohome !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, addfood_user_private::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, addfood_user_private::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }


        }

    }






    fun loadfood(s: String) {
        val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${UID}")
        if (s != null) {
            val firebaseSrarchQuery: Query =
                ref.orderByChild("namefood").startAt(s).endAt(s + "\uf8ff")
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

                        if(backtohome !=""){
                            val backtohome = "homeselectfood"
                            val intent = Intent(view.context, savedatafood_user_private::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("backtohome",backtohome)
                            intent.putExtra("namefood", fooditem.food.namefood)
                            intent.putExtra("kcalfood", fooditem.food.kcal)
                            intent.putExtra("id", fooditem.food.id_food)
                            startActivity(intent)
                            finish()

                        }else{
                            val intent = Intent(view.context, savedatafood_user_private::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("namefood", fooditem.food.namefood)
                            intent.putExtra("kcalfood", fooditem.food.kcal)
                            intent.putExtra("id", fooditem.food.id_food)
                            startActivity(intent)
                            finish()

                        }



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
                        Log.d("text", food.toString())
                        if (food != null) {
                            adapter.add(Food(food))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val fooditem = item as Food
                        val intent = Intent(view.context, savedatafood_user_private::class.java)
                        intent.putExtra("namefood", fooditem.food.namefood)
                        intent.putExtra("kcalfood", fooditem.food.kcal)
                        intent.putExtra("id", fooditem.food.id_food)
                        startActivity(intent)
                        finish()
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
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, selectlistfood_user::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()
    }
}
