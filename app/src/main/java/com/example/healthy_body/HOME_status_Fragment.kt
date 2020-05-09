package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.healthy_body.calculate.dateselect_totalvalue
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_listfood_.*
import kotlinx.android.synthetic.main.fragment_home_listfood_.view.*
import kotlinx.android.synthetic.main.fragment_home_status_.*
import kotlinx.android.synthetic.main.fragment_home_status_.view.*
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HOME_status_Fragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var UID = arguments!!.getString("UID")
        val Timeargument = arguments!!.getString("time")
        Log.e("timeagument_status",Timeargument)
        val progest  = ProgressDialog(this@HOME_status_Fragment.context,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()
        val v = inflater.inflate(R.layout.fragment_home_status_, container, false)
        myRef = FirebaseDatabase.getInstance().reference

        v.addworkout.setOnClickListener {
            val intent = Intent(this.context,selectlistexcercise_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            getActivity()!!.finish()
        }

        v.addfood.setOnClickListener{
            val intent = Intent(this.context,selectlistfood_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            getActivity()!!.finish()
        }




        val TDEEshow = v.findViewById<TextView>(R.id.numTDEE)
        val BMIshow = v.findViewById<TextView>(R.id.numBMI)
        val BMRshow = v.findViewById<TextView>(R.id.numBMR)

        dateselect_totalvalue(UID.toString(),Timeargument.toString()).callvalue{ excercise, food ->
            var Food = food.toInt()
            var  Workout = excercise.toInt()

            if(Food==null && Workout == null){
                val chart = v.findViewById(R.id.chart) as PieChart
                pieimage.isVisible = true

            }else{
                pieimage.isVisible = false
                chart.isVisible = true
                val peercenData = intArrayOf(Food, Workout)
                val pnameFood = arrayOf("อาหาร", "กิจกกรม")

                val pieEntries = ArrayList<PieEntry>()
                for (i in peercenData.indices) {
                    pieEntries.add(
                        PieEntry(
                            peercenData[i].toFloat(),
                            pnameFood[i]
                        )
                    )//ค่าที่เก็บจะต้องเป็น array
                }

                val dataSet = PieDataSet(pieEntries, "KCAL")
                val colors = ArrayList<Int>()
                colors.add(Color.rgb(171,69,204))
                colors.add(Color.rgb(51,102,255))
                dataSet.colors = colors
                val data = PieData(dataSet)
                val chart = v.findViewById(R.id.chart) as PieChart
                chart.getDescription().setEnabled(false)
                chart.getLegend().setEnabled(false)
                chart.data = data
                chart.isDrawHoleEnabled = false
                chart.centerTextRadiusPercent
                chart.setCenterTextSizePixels(500f)
                data.setValueTextSize(30f)
                chart.animateY(500)
                chart.invalidate()
            }
        }


        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map = dataSnapshot.value as Map<*, *>?
                val map1 = dataSnapshot.child("users").child(UID.toString()).value as Map<*, *>?
                val BMI = map1!!["bmis"].toString()
                val BMR = map1!!["BMR"].toString()
                val TDEE = map1["TDEE"].toString()
                if(BMI !=""){
                    progest.cancel()
                    TDEEshow.text = TDEE
                    BMIshow.text = BMI
                    BMRshow.text = BMR
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


        return v
    }


    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HOME_status_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
