package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_addfood_user.*
import kotlinx.android.synthetic.main.activity_home__user.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.barselect.view.*

class addfood_user : AppCompatActivity(), View.OnClickListener {
     val UID="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    val ref = FirebaseDatabase.getInstance().getReference("FOOD")
    var sum  = 1
    var resultBig :Int=0
   // var UID :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfood_user)
       // UID = intent.getStringExtra("UID")
       val amount = findViewById<TextView>(R.id.textView6)
       amount.setText("${sum}")
       var add =findViewById<Button>(R.id.add)
       var sub  = findViewById<Button>(R.id.sub)

       add.setOnClickListener(this)
       sub.setOnClickListener(this)


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, selectlistfood_user::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)
        }
          /*  ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        println(p0)
                        Log.e("data", "data notnull")
                        for (list in p0.children) {
                            val s = list!!.getValue(daat::class.java)
                            val key = list!!.key
                            var test = list!!.value.toString()
                            var textiinfo = s!!.name

                            val intkey = key!!.toInt()
                            button.setOnClickListener {
                                val name = name.text.toString()
                                Log.d("nameuser in put", "${name}")
                                if (name == textiinfo) {

                                    val ref = FirebaseDatabase.getInstance().getReference("testnum")
                                        .child(key)
                                    Log.e("pass", "if")
                                    Log.d("pass updatevalue", "${name}")
                                    val childUpdates = HashMap<String, String>()
                                    childUpdates.put("name", "${name}")
                                    ref.updateChildren(childUpdates as Map<String, Any>)


                                } else {
                                    Log.e("pass", "else")
                                    val key = list!!.key
                                    val intkey = key!!.toInt()

                                    println("textname => ${name}")
                                    Log.d("pass setvalue", "${name}")

                                    val newkey = intkey + 1
                                    val keystrig = newkey.toString()
                                    ref.child("${keystrig}").child("name").setValue(name)
                                }
                            }
                        }
                    } else {
                        Log.e("data", "data null")
                        buttonaddfood.setOnClickListener {
                            val namefood = inputname.text.toString()
                            val kcal = inputkcal.text.toString()
                            val unit = inputunit.text.toString()
                            val typeunit = inputtypeunit.text.toString()


                            /*   Log.e("pass", "${name}")
                            Log.e("pass", "button")
                            val key: Int = 0
                            println("textname => ${name}")
                            Log.d("pass setvalue", "${name}")
                            val newkey = key + 1
                            val keystrig = newkey.toString()
                            ref.child("${keystrig}").child("name").setValue(name)*/


                        }
                    }
                }
            })*/






    }
    override fun onClick(v: View?) {
       //val sumInt= sum.toInt()

        var subsum = 1
        when (v?.id) {
            R.id.add -> {
                resultBig = resultBig + 1
                Log.d("resultBig","$resultBig")
               // val textsum = reesult.toString()
                textView6.setText("${resultBig}")

            }
            R.id.sub -> {
                resultBig = resultBig - subsum
                Log.d("resultBig","$resultBig")
               // val textsum = reesult.toString()
                textView6.setText("${resultBig}")
            }
            else -> {
            }
        }
    }
}
