package com.example.healthy_body

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_savedatafood_user.*

class list_saveedit_food : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saveedit_food)
        var add =findViewById<Button>(R.id.add)
        var sub  = findViewById<Button>(R.id.sub)
    }

    override fun onClick(v: View?) {
       /* Log.d("kcalfoodShow","${nameFoodShowB}")
        Log.d("kcalfoodShow","${kcalfoodShowB}")
        var sumkcalsub = kcalfoodShowB.toInt()
        Log.d("kcalfoodShow","${sumkcalsub}")
        var sumkcal = kcalfoodShowB.toInt()

        when (v?.id) {
            R.id.add -> {
                sum = sum + 1
                resultBig = sumkcal*sum
                Log.d("sumkcal add =>","${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            R.id.sub -> {
                sum = sum - 1
                resultBig = resultBig - sumkcalsub
                Log.d("sumkcal sub =>","${resultBig}")
                Log.d("sum","${sum}")
                amount.setText("$sum")
                tatal.setText("$resultBig")
            }
            else -> {

        }*/
    }

}
