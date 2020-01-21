package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.healthy_body.model.modellistexcercise
import com.example.healthy_body.model.modellistfood
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectlistexcercise_user : AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().getReference("EXCERCISE")
    var UID :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistexcercise_user)

      UID = intent.getStringExtra("UID")


        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter
        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
            var searchtext = search.text.toString()
            loadexcercise(searchtext)
        }


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.addexcercise) as ImageView
        imageView.setOnClickListener {
            val intent = Intent(this, addexcercise_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }
    }
    fun loadexcercise(s: String) {
        if (s != null) {
            val firebaseSrarchQuery: Query =
                ref.orderByChild("name_excercise").startAt(s).endAt(s + "\uf8ff")
            Log.d("firebaseSrarchQuery", "${firebaseSrarchQuery}")
            val adapter = GroupAdapter<ViewHolder>()
            firebaseSrarchQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        Log.d("text", it.toString())
                        val excercise = it.getValue(modellistexcercise::class.java)
                        if (excercise != null) {
                            adapter.add(Excercise(excercise))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val excerciseitem = item as selectlistexcercise_user.Excercise
                        val intent = Intent(view.context, savedataexcercise_user::class.java)
                        intent.putExtra("UID",UID)
                        intent.putExtra("nameexcercise", excerciseitem.excercise.name_excercise)
                        intent.putExtra("kcalexcercise", excerciseitem.excercise.kcal)
                        intent.putExtra("id", excerciseitem.excercise.id_excercise)
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
                        val excercise = it.getValue(modellistexcercise::class.java)
                        if (excercise != null) {
                            adapter.add(Excercise(excercise))
                        }
                    }
                    adapter.setOnItemClickListener { item, view ->
                        val fooditem = item as selectlistfood_user.Food
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
    inner class Excercise(val excercise: modellistexcercise) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Log.d("name_food", excercise.name_excercise)
            Log.d("kcal_String", excercise.kcal)
            viewHolder.itemView.name.text = excercise.name_excercise
            viewHolder.itemView.kcal.text = excercise.kcal
            Log.d("viewHolder", "${viewHolder}")


        }

        override fun getLayout(): Int {
            return R.layout.list_food
        }
    }
    }



