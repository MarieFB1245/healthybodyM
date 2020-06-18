package com.example.healthy_body

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.calculate.process_image
import com.example.healthy_body.model.modellistfood
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home__user.*
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.barselect.*
import kotlinx.android.synthetic.main.dialod_list.*
import kotlinx.android.synthetic.main.list_food.view.*
import kotlinx.android.synthetic.main.list_food.view.kcal
import kotlinx.android.synthetic.main.list_food.view.name
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class selectlistfood_user : AppCompatActivity() {
 //   val UID="Ph0BSgJTuLUluUI7IpGMcDPCeBx2"
 private var doubleBackToExitPressedOnce = false
    var ref = FirebaseDatabase.getInstance().getReference("FOOD")
    var UID :String=""
 val CAMERA_REQUEST_CODE =0
    var searchtext :String=""
    //private val numbermassen :Int
    val texttest = "test"
var text:String=""
    private var numtext:String =""

var back_home_add:String =""
    var namefoodstring :String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectlistfood_user)

        UID = intent.getStringExtra("UID")


        if(intent.getStringExtra("back_home_add")!=null) back_home_add = intent.getStringExtra("back_home_add")


        Log.e("UID =>","${UID}")
        Log.e("back_home_add =>","${back_home_add}")

        val adapter = GroupAdapter<ViewHolder>()
        mRecycleVeiew.adapter = adapter


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





        loadfood(searchtext)

        var search = findViewById<EditText>(R.id.Searching)
        sreachtext.setOnClickListener {
             searchtext = search.text.toString()
            Log.e("searchtext","$searchtext")
            loadfood(searchtext)
        }




        val arrow = findViewById<ImageView>(R.id.arrow)
        val imageadd = findViewById<ImageView>(R.id.addfood)
        val imagelist = findViewById<ImageView>(R.id.addprivate)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar1)
        setSupportActionBar(tooltset)


        imagelist.setOnClickListener {
            if(back_home_add !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, selectelistfood_user_private::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, selectelistfood_user_private::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }

        }

        arrow.setOnClickListener {
            if(back_home_add !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, Home_User::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, Home_User::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }

        }

        imageadd.setOnClickListener {
            if(back_home_add !=""){
                val backtohome = "homeselectfood"
                val intent = Intent(this, addfood_user::class.java)
                intent.putExtra("UID",UID)
                intent.putExtra("backtohome",backtohome)
                startActivity(intent)
                finish()

            }else{

                val intent = Intent(this, addfood_user::class.java)
                intent.putExtra("UID",UID)
                startActivity(intent)
                finish()

            }
           }

        camera.setOnClickListener {

            val startcamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(startcamera,CAMERA_REQUEST_CODE)

        }





        listselect.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialod_list)

            var calendar = Calendar.getInstance()
            val myFormat = "dd-M-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val date  = sdf.format(calendar.getTime())
            Log.e("date","${date}")
            var adapters = GroupAdapter<ViewHolder>()
           val ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child(date)
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("p0", p0.toString())
                    if(p0.exists()){
                        p0.children.forEach {
                            Log.d("DataSnapshot", it.toString())
                            val listfood = it.getValue(dataselectfood::class.java)
                            Log.d("text", listfood.toString())
                            if (listfood != null) {
                                adapters.add(Foodlist(listfood))

                            }
                            dialog.recyclerView2.adapter = adapters
                        }

                    }else{

                    }

                }
                override fun onCancelled(p0: DatabaseError) {}
            })
            dialog.imagebutton_cancel.setOnClickListener {
                dialog.cancel()
            }
            dialog.show()


        }
        loadlistnumber()



    }

    private fun loadlistnumber() {
        var number = 0
        var calendar = Calendar.getInstance()
        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val date  = sdf.format(calendar.getTime())
        Log.e("date","${date}")
        var adapters = GroupAdapter<ViewHolder>()
       val ref = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID}").child(date)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("p0", p0.toString())
                if(p0.exists()){
                    buttonfoodnumber.isVisible = true
                    p0.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(dataselectfood::class.java)
                        Log.d("text", listfood.toString())
                        number = number +1
                        buttonfoodnumber.setText("$number")
                    }

                }else{
                    buttonfoodnumber.isVisible = false

                }

            }
            override fun onCancelled(p0: DatabaseError) {}
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(resultCode == Activity.RESULT_OK && data != null){

                    val dataimage  = (data.extras!!.get("data") as Bitmap)
                    val stream = ByteArrayOutputStream()
                    stream.close()
                    Log.e("dataimage","${dataimage}")
                    Log.e("dataimage","${requestCode}")
                    Log.e("dataimageq","${resultCode}")

                  process_image(dataimage).process{namefood ->
                      var search = findViewById<EditText>(R.id.Searching)
                      search.setText(namefood)
                      //loadfood(namefood)

                      namefoodstring = namefood
                      Log.e("name","$namefood")
                      if(namefoodstring =="") {

                      }else{
                          ref.orderByChild("namefood")
                          ref.addListenerForSingleValueEvent(object : ValueEventListener{
                              override fun onCancelled(p0: DatabaseError) {
                              }

                              override fun onDataChange(p0: DataSnapshot) {
                                  p0.children.forEach {
                                      val food = it.getValue(modellistfood::class.java)
                                      if(food!!.namefood == namefood) {
                                          Log.e("name","$namefood")

                                          if(back_home_add !=""){

                                              val backtohome = "homeselectfood"
                                              val intent = Intent(this@selectlistfood_user, savedatafood_user::class.java)
                                              intent.putExtra("UID",UID)
                                              intent.putExtra("backtohome",backtohome)
                                              intent.putExtra("namefood", food.namefood)
                                              intent.putExtra("kcalfood", food.kcal)
                                              intent.putExtra("id", food.id_food)
                                              startActivity(intent)
                                              finish()

                                          }else{

                                              val intent = Intent(this@selectlistfood_user, savedatafood_user::class.java)
                                              intent.putExtra("UID",UID)
                                              intent.putExtra("namefood", food.namefood)
                                              intent.putExtra("kcalfood", food.kcal)
                                              intent.putExtra("id", food.id_food)
                                              startActivity(intent)
                                              finish()

                                          }


                                      }else{
                                          Log.e("data","DON have")
                                          SweetAlertDialog(this@selectlistfood_user, SweetAlertDialog.ERROR_TYPE)
                                              .setTitleText("ไม่ค้นพบที่ค้นหา")
                                              .setContentText("กรุณาลองถ่ายใหม่อีกครั้ง")
                                              .setConfirmText("ตกลง")
                                              .showCancelButton(true)
                                              .setCancelClickListener { sDialog -> sDialog.cancel() }
                                              .show()
                                      }
                                  }
                              }

                          })
                      }




                  }

                }

            }else -> {
            Log.e("ERROR","CAMERA")
        }
        }


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
                            if(back_home_add !=""){
                                val backtohome ="homeselectfood"
                                val intent = Intent(view.context,savedatafood_user::class.java)
                                intent.putExtra("UID",UID)
                                intent.putExtra("backtohome",backtohome)
                                intent.putExtra("namefood", fooditem.food.namefood)
                                intent.putExtra("kcalfood", fooditem.food.kcal)
                                intent.putExtra("id", fooditem.food.id_food)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent = Intent(view.context, savedatafood_user::class.java)
                                intent.putExtra("UID",UID)
                                intent.putExtra("namefood", fooditem.food.namefood)
                                intent.putExtra("kcalfood", fooditem.food.kcal)
                                intent.putExtra("id", fooditem.food.id_food)
                                startActivity(intent)
                                finish()
                            }

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
                        if(back_home_add !=""){
                            val backtohome ="homeselectfood"
                            val intent = Intent(view.context,savedatafood_user::class.java)
                            intent.putExtra("UID",UID)
                            intent.putExtra("backtohome",backtohome)
                            intent.putExtra("namefood", fooditem.food.namefood)
                            intent.putExtra("kcalfood", fooditem.food.kcal)
                            intent.putExtra("id", fooditem.food.id_food)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(view.context,savedatafood_user::class.java)
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

    inner class Foodlist(val listfood: dataselectfood) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= listfood.nameFoodShowB
            viewHolder.itemView.kcal.text = listfood.kcalfoodShowB
            Log.d("viewHolder", "${viewHolder}")
        }

        override fun getLayout(): Int {
            return R.layout.listfood_show

        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        if(back_home_add != null){
            val backtohome = "homeselectfood"
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            intent.putExtra("backtohome",backtohome)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, Home_User::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }
    }




}
