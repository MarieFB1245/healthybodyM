package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.healthy_body.model.modellistexcercise
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.list_food.view.*

class selectlistexcercise_user_private : AppCompatActivity() {

    //var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"

    var UID :String=""
    var searchtext :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistexcercise_user_private)

        UID = intent.getStringExtra("UID")

        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter

        loadexcercise(searchtext)

        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
            searchtext = search.text.toString()
            loadexcercise(searchtext)
        }


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)

        arrow.setOnClickListener {
            val intent = Intent(this, selectlistexcercise_user::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.addexcercise_private) as ImageView
        imageView.setOnClickListener {
            val intent = Intent(this, addexcerciser_user_private::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)
        }
    }
    fun loadexcercise(s: String) {
        val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${UID}")
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
                        val excerciseitem = item as Excercise
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
                        val excerciseitem= item as Excercise
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
