package com.example.healthy_body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.healthy_body.calculate.savemenufood_private
import com.google.firebase.database.FirebaseDatabase
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_addfood_user.*

class addfood_user_private_Bsetting : AppCompatActivity(), View.OnClickListener  {
    private var doubleBackToExitPressedOnce = false
    //val UID="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
    val ref = FirebaseDatabase.getInstance().getReference("DATA_PRIVATE_FOOD")
    var sum  = 1
    var amount :Int=1
    var UID :String=""

    internal var SPINNERLST = arrayOf("อาหารจานเดี่ยว/กับข้าว","เครื่องดื่ม","ขนม/ของหวาน",
        "ผลไม้")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addfood_user_private__bsetting)

        UID = intent.getStringExtra("UID")
        val amount = findViewById<TextView>(R.id.textView6)
        amount.setText("${sum}")


        inputkcal.setTransformationMethod(null)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLST)
        val betterSpinner = findViewById(R.id.inputtypefood) as MaterialBetterSpinner
        betterSpinner.setAdapter(arrayAdapter)

        val inputname = findViewById<EditText>(R.id.inputname)
        val inputkcal = findViewById<EditText>(R.id.inputkcal)
        val inputunit = findViewById<EditText>(R.id.inputunit)
        val inputtypeunit = findViewById<EditText>(R.id.inputtypeunit)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)

        add.setOnClickListener(this)
        sub.setOnClickListener(this)


        val arrow = findViewById<ImageView>(R.id.arrow)
        val tooltset = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(tooltset)
        arrow.setOnClickListener {
            val intent = Intent(this, list_edit_food_private::class.java)
            intent.putExtra("UID", UID)
            startActivity(intent)
            finish()
        }



        buttonaddfood.setOnClickListener {
            val namefood = inputname.text.toString()
            val kcal = inputkcal.text.toString()
            val unit = inputunit.text.toString()
            val typefood = betterSpinner.text.toString()
            if(namefood!= ""&&kcal!=""&&unit!=""&&typefood!=""&&this.amount!=null){
                savemenufood_private(UID,namefood,kcal,unit,typefood,this.amount).save()
                val intent = Intent(this, list_edit_food_private::class.java)
                intent.putExtra("UID", UID)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please in put Information Food", Toast.LENGTH_SHORT).show()
            }



        }


    }

    override fun onClick(v: View?) {
        //val sumInt= sum.toInt()

        var subsum = 1
        when (v?.id) {
            R.id.add -> {
                amount = amount + 1
                Log.d("resultBig","$amount")
                // val textsum = reesult.toString()
                textView6.setText("${amount}")

            }
            R.id.sub -> {
                amount = amount - subsum

                if(amount<=1){
                    amount = 1
                }
                Log.d("resultBig","$amount")
                // val textsum = reesult.toString()
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
        intent.putExtra("UID", UID)
        startActivity(intent)
        finish()

    }
}
