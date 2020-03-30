package com.example.healthy_body

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.healthy_body.calculate.delectdata
import com.example.healthy_body.calculate.delectdata_private
import com.example.healthy_body.calculate.savetotalkcal
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_addfood_user.*
import kotlinx.android.synthetic.main.activity_addfood_user_private.*
import kotlinx.android.synthetic.main.activity_addfood_user_private.textView6
import kotlinx.android.synthetic.main.activity_list_saveedit_food.*
import kotlinx.android.synthetic.main.activity_list_saveedit_food_menu_private.*

class list_saveedit_food_menu_private : AppCompatActivity(), View.OnClickListener {

    private var doubleBackToExitPressedOnce = false
    var nameFoodShow: String = ""
    var kcalfoodShow: String = ""
    var amount: String = ""
    var UID: String = ""
    var unit: String = ""
    var unittype : String = ""
    var id_food :String = ""
var textamountF :String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_food_menu_private)


        id_food = intent.getStringExtra("id_food")
        UID = intent.getStringExtra("UID")
        nameFoodShow = intent.getStringExtra("namefood")
        kcalfoodShow = intent.getStringExtra("kcal")
        amount = intent.getStringExtra("amount")
        unit = intent.getStringExtra("unit")
        unittype = intent.getStringExtra("unittype")

        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)
        add.setOnClickListener(this)
        sub.setOnClickListener(this)

       val name = findViewById<EditText>(R.id.inputname)
        val kcal = findViewById<EditText>(R.id.inputkcal)
        val textamount = findViewById<TextView>(R.id.textView6)
        val textunit = findViewById<EditText>(R.id.inputunit)
        val texttypeunit = findViewById<EditText>(R.id.inputtypeunit)


        name.setText(nameFoodShow)
        kcal.setText(kcalfoodShow)
        textamount.setText(amount)
        textunit.setText(unit)
        texttypeunit.setText(unittype)






        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, list_edit_food_private::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
        }

        val delect = findViewById<ImageView>(R.id.dalect)
        delect.setOnClickListener {
            var textnameF = name.text.toString()
            var textkcalF = kcal.text.toString()
            textamountF = textamount.text.toString()
            var textunitF = textunit.text.toString()
            var texttypeunitF = texttypeunit.text.toString()
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("คุณเเน่ใจ?")
                .setContentText("ว่าต้องการลบรายการนี้!")
                .setCancelText("ไม่ต้องการ!")
                .setConfirmText("ต้องการ!")
                .showCancelButton(true)
                .setCancelClickListener { sDialog -> sDialog.cancel() }
                .setConfirmClickListener {
                    val resultB =  delectdata_private(id_food,UID,textnameF, textkcalF,textamountF,textunitF,texttypeunitF).deelect()
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
                                val intent = Intent(this, list_edit_food_private::class.java)
                                intent.putExtra("UID",UID)
                                startActivity(intent)}
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    }
                    pDialog.show()

                }
                .show()
        }


        Editfoodprivate.setOnClickListener {
            var textnameF = name.text.toString()
            var textkcalF = kcal.text.toString()
            textamountF = textamount.text.toString()
            var textunitF = textunit.text.toString()
            var texttypeunitF = texttypeunit.text.toString()

                  updatetodata_private(textnameF,textkcalF,textamountF,textunitF,texttypeunitF)
                  val intent = Intent(this,list_edit_food_private::class.java)
                  intent.putExtra("UID",UID)
                  startActivity(intent)

              }



    }

    private fun updatetodata_private(textnameF: String, textkcalF: String, textamountF: String, textunitF: String, texttypeunitF: String) {

        var ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD").child("${UID}").child("$id_food")
        val childUpdates = HashMap<String, Any>()
        childUpdates.put("namefood", textnameF)
        childUpdates.put("kcal", textkcalF)
        childUpdates.put("amount", textamountF)
        childUpdates.put("unit", textunitF)
        childUpdates.put("unittype", texttypeunitF)
        ref.updateChildren(childUpdates as Map<String, Any>)
    }
    override fun onClick(v: View?) {
        var subsum = 1
        var amountInt =amount.toInt()
var  total :Int = 0
        when (v?.id) {
            R.id.add -> {
                amountInt = amountInt + 1
                Log.d("resultBig","$amount")
                 amount = amountInt.toString()
                textView6.setText("${amount}")

            }
            R.id.sub -> {
                amountInt = amountInt - subsum
                if(amountInt<=1){
                    amountInt = 1
                }
                amount = amountInt.toString()
                textView6.setText("${amount}")
            }
            else -> {
            }
        }
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        val intent = Intent(this, list_edit_food_private::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)

    }
}
