package com.example.healthy_body

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.calculate.delectdata_private_excercise
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addexcercise_user.*

class list_saveedit_excercise_menu_private : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    var UID :String=""
    var id_excercise :String=""
    var name_excercise :String=""
    var kcal :String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_excercise_menu_private)

        inputkcal.setTransformationMethod(null)
        UID = intent.getStringExtra("UID")
        id_excercise = intent.getStringExtra("id_excercise")
        name_excercise = intent.getStringExtra("name_excercise")
        kcal = intent.getStringExtra("kcal")

        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, list_edit_excercise_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            finish()
        }

        val inputnameexcercise = findViewById<EditText>(R.id.inputnameexcercise)
        val inputkcal = findViewById<EditText>(R.id.inputkcal)
        inputkcal.setTransformationMethod(null)
        inputnameexcercise.setText(name_excercise)
        inputkcal.setText(kcal)

        val delect = findViewById<ImageView>(R.id.dalect)
        delect.setOnClickListener {
            Log.e("id_excercise","${id_excercise}")
            Log.e("id_excercise","${UID}")
            val textnameEX = inputnameexcercise.text.toString()
            val textkcal = inputkcal.text.toString()
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("คุณเเน่ใจ?")
                .setContentText("ว่าต้องการลบรายการนี้!")
                .setCancelText("ไม่ต้องการ!")
                .setConfirmText("ต้องการ!")
                .showCancelButton(true)
                .setCancelClickListener { sDialog -> sDialog.cancel() }
                .setConfirmClickListener {
                    val resultB = delectdata_private_excercise(id_excercise,UID,textnameEX,textkcal).delectex()
                    val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
                    pDialog.titleText = "กำลังทำการลบรายการ"
                    pDialog.setCancelable(false)
                    if (resultB.equals(true)){
                        pDialog.setCancelable(true)
                        pDialog
                            .setTitleText("ลบเรียบร้อยเเล้ว!")
                            .setContentText("ข้อมูลนี้จะไม่มีอยู่รายการของคุณ!").setConfirmText("ตกลง")
                            .setConfirmClickListener{
                                val intent = Intent(this, list_edit_excercise_private::class.java)
                                intent.putExtra("UID",UID)
                                startActivity(intent)
                                finish()
                            }
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    }
                    pDialog.show()

                }
                .show()
        }



        buttonaddexceercisee.setOnClickListener {
            val textnameEX = inputnameexcercise.text.toString()
            val textkcal = inputkcal.text.toString()
            if (textnameEX !=""&&textkcal!=""){
                update_excercise_private(textnameEX,textkcal)
                val intent = Intent(this, list_edit_excercise_private::class.java)
                intent.putExtra("UID", UID)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please in put Information Excercise", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun update_excercise_private( textnameEX: String, textkcal: String) {
        var ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_EXCERCISE").child("${UID}").child("${id_excercise}")
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("kcal", textkcal)
        childUpdates.put("name_excercise", textnameEX)
        ref.updateChildren(childUpdates as Map<String, Any>)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, selectlistexcercise_user::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)
        finish()
    }
}
