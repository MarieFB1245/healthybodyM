package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import cn.pedant.SweetAlert.SweetAlertDialog
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_register__infirmation.*
import kotlin.math.pow
import java.text.DecimalFormat
import com.google.firebase.auth.FirebaseAuth
import com.example.healthy_body.calculate.register
import com.google.firebase.database.*


class Register_Infirmation : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    internal var SPINNERLST = arrayOf("น้อย หรือไม่ค่อยออกกำลังกาย","ปานกลาง ออกกำลังกาย 1-3 ครั้งต่อสัปดาห์","ปานกลาง ออกกำลังกาย 4-5 ครั้งต่อสัปดาห์",
        "หนัก ออกกำลังกาย 6-7 ครั้งต่อสัปดาห์","หนักมาก ออกกำลังกายวันละ 2 ครั้งขึ้นไป")


    internal var SPINNERLSTGENDER = arrayOf("ชาย","หญิง")
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase

    private var myRef = FirebaseAuth.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register__infirmation)

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, Register_User::class.java)
            startActivity(intent)
            finish()
        }


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.android_material_desgn_spinner) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)

        val arrayAdaptergender = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLSTGENDER)
        val betterSpinnergender = findViewById(R.id.spinner_gender) as MaterialBetterSpinner
        betterSpinnergender.setAdapter(arrayAdaptergender)

        var email: String = intent.getStringExtra("email")
        var password: String = intent.getStringExtra("password")


        registerbutton.setOnClickListener {

            var LENGTH_LONG  = 3500

           val ss = Toast.makeText(this, "Process information...",  Toast.LENGTH_LONG)

            myRef = FirebaseAuth.getInstance()
            myRef.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful)
                    Log.d("Main","saved")


                var firstname  =  findViewById<EditText>(R.id.inputfirstname)
                var lastname  =  findViewById<EditText>(R.id.inputLastname)
                var textfirstname = firstname.text.toString()
                var textlastname = lastname.text.toString()
                val df = DecimalFormat("0.00")
                val Level_Workout = betterSpinner.text.toString()
                var textheight = findViewById<EditText>(R.id.inputheight)
                var textweigth = findViewById<EditText>(R.id.inputweight)
                var textage = findViewById<EditText>(R.id.inputage)



                if(textheight.getText().toString() =="" && textheight.getText().toString() ==""&&textfirstname==""&&textlastname=="" ){
                    Toast.makeText(this, "Please in put information in correct.", Toast.LENGTH_SHORT).show()
                }else{
                    val height = java.lang.Float.valueOf(textheight.getText().toString())
                    val weigth = java.lang.Float.valueOf(textweigth.getText().toString())
                    val age = java.lang.Float.valueOf(textage.getText().toString())
                    val Reheight = height/100f //คำนวนใน oop
                    val totalBMI= weigth/(Reheight.pow(2))
                    val BMIS = df.format(totalBMI)
                    var gender  = betterSpinnergender.text.toString()
                    var uid = FirebaseAuth.getInstance().uid?:""
                    if(email ==""&&password==""&&age==null&&height==null&&weigth==null&&BMIS==""&&gender==""&&Level_Workout==""&&textfirstname==""&&textlastname==""){
                        Toast.makeText(this, "Please in put information in correct.", Toast.LENGTH_SHORT).show()
                    }else{
                        register(uid,email,password,age,height,weigth,BMIS,gender,Level_Workout,textfirstname,textlastname).regis()

                        val myRef = FirebaseDatabase.getInstance().reference
                        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                val map1 = p0.child("users").child(uid).value as Map<*, *>? //ดึงข้อมูล มา จาด UID
                                val status = map1!!["status"].toString()//วางค่า ที่ได้จาก dataSnapshot
                                val TDEE = map1["TDEE"].toString()//วางค่า ที่ได้จาก dataSnapshot
                                intent(uid,status,TDEE)
                                finish()
                            }

                        })


                    }

                }






            }.addOnFailureListener {
                Log.d("informaton","error")
            }


        }


}
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
                val intent = Intent(this, Register_User::class.java)
                startActivity(intent)
                finish()



    }

    fun intent (uid: String,status:String,TDEE:String){


        val intent = Intent(this, Totalkcal_User::class.java)
        intent.putExtra("UID",uid)
        intent.putExtra("status",status)
        intent.putExtra("TDEE",TDEE)
        startActivity(intent)
        finish()
    }
}


