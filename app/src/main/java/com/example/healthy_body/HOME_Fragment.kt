package com.example.healthy_body

import android.app.DatePickerDialog
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
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import com.example.healthy_body.calculate.dateselect_totalvalue
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_savedatafood_user.*
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlinx.android.synthetic.main.fragment_home_.view.*
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HOME_Fragment : Fragment() {

    var timeselect_Fragment:String=""
    private var Numberfragment: String="0"
    var UID :String?=""
    var backtohome:String?=""
    var number_add = 0
    private val listener: OnFragmentInteractionListener? = null
    var calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home_, container, false)
         UID = arguments!!.getString("UID")
        backtohome = arguments!!.getString("backtohome")

        Log.e("backtohome fragment_home","$backtohome")
        v.buttonRe.setOnClickListener {
            val intent = Intent(this.context,dashboard_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            getActivity()!!.finish()
        }
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        v.buttoncalenda_Fragment.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(this@HOME_Fragment.requireContext(),
                    R.style.DialogTheme,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()


            }



        })

if(backtohome==null){
    val myFormat = "dd-M-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    var data = sdf.format(calendar.getTime())
    timeselect_Fragment=data
    v.line1.isVisible = true
    v.addmenu.isVisible = false
    v.list_food_fragment.setTextColor(Color.parseColor("#808080"))
    v.status_information_fragment.setTextColor(Color.WHITE)
    v.list_excersice_fragment.setTextColor(Color.parseColor("#808080"))
    val textFragment = HOME_status_Fragment()
    val bundle = Bundle()
    bundle.putString("UID","$UID")
    bundle.putString("time","$timeselect_Fragment")
    textFragment.setArguments(bundle)
    val transaction = childFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()
    Numberfragment = "0"
    v.status_information_fragment.setEnabled(false)
}else if(backtohome == "homeselectfood"){
    val myFormat = "dd-M-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    var data = sdf.format(calendar.getTime())
    timeselect_Fragment=data
    v.addmenu.isVisible = true
    v.addmenu.setText("เพิ่มรายการอาหาร")

    number_add = 1
    v.line1.isVisible = false
    v.line2.isVisible = true
    v.line3.isVisible = false
    val relativeLayout = activity!!.findViewById(R.id.home_user) as RelativeLayout
    relativeLayout.setBackgroundResource(R.drawable.backgroud_status )
    v.list_food_fragment.setEnabled(false)
    v.status_information_fragment.setEnabled(true)
    v.list_excersice_fragment.setEnabled(true)
    Numberfragment = "1"
    v.list_food_fragment.setTextColor(Color.WHITE)
    v.status_information_fragment.setTextColor(Color.parseColor("#808080"))
    v.list_excersice_fragment.setTextColor(Color.parseColor("#808080"))
    val textFragment = HOME_listfood_Fragment()
    val bundle = Bundle()
    bundle.putString("UID","$UID")
    bundle.putString("time","$timeselect_Fragment")
    textFragment.setArguments(bundle)
    val transaction = childFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right);
    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()


}else{
    val myFormat = "dd-M-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.US)
    var data = sdf.format(calendar.getTime())
    timeselect_Fragment=data
    v.addmenu.isVisible = true
    v.addmenu.setText("เพิ่มรายการกิจกกรม")
    number_add = 2
    v.line1.isVisible = false
    v.line2.isVisible = false
    v.line3.isVisible = true
    val relativeLayout = activity!!.findViewById(R.id.home_user) as RelativeLayout
    relativeLayout.setBackgroundResource(R.drawable.backgroud_status )
    v.list_excersice_fragment.setEnabled(false)
    v.list_food_fragment.setEnabled(true)
    v.status_information_fragment.setEnabled(true)
    Numberfragment = "2"
    v.list_food_fragment.setTextColor(Color.parseColor("#808080"))
    v.status_information_fragment.setTextColor(Color.parseColor("#808080"))
    v.list_excersice_fragment.setTextColor(Color.WHITE)
    val textFragment = HOME_listexcersice_Fragment()
    val bundle = Bundle()
    bundle.putString("time","$timeselect_Fragment")
    bundle.putString("UID","$UID")
    bundle.putString("time","$timeselect_Fragment")
    textFragment.setArguments(bundle)
    val transaction = childFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_left);
    transaction.replace(R.id.fragment_container,textFragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

Log.e("time","$timeselect_Fragment")




        v.status_information_fragment.setOnClickListener {
            v.addmenu.isVisible = false
            v.line1.isVisible = true
            v.line2.isVisible = false
            v.line3.isVisible = false
            val relativeLayout = activity!!.findViewById(R.id.home_user) as RelativeLayout
            relativeLayout.setBackgroundResource(R.drawable.backgroud_status )
            v.status_information_fragment.setEnabled(false)
            v.list_food_fragment.setEnabled(true)
            v.list_excersice_fragment.setEnabled(true)
            Numberfragment = "0"
            list_food_fragment.setTextColor(Color.parseColor("#808080"))
            status_information_fragment.setTextColor(Color.WHITE)
            list_excersice_fragment.setTextColor(Color.parseColor("#808080"))
            val textFragment = HOME_status_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
        v.list_food_fragment.setOnClickListener {
           v.addmenu.isVisible = true
            v.addmenu.setText("เพิ่มรายการอาหาร")
            number_add = 1
            v.line1.isVisible = false
            v.line2.isVisible = true
            v.line3.isVisible = false
         val relativeLayout = activity!!.findViewById(R.id.home_user) as RelativeLayout
            relativeLayout.setBackgroundResource(R.drawable.backgroud_status )


            v.list_food_fragment.setEnabled(false)
            v.status_information_fragment.setEnabled(true)
            v.list_excersice_fragment.setEnabled(true)
            Numberfragment = "1"
            list_food_fragment.setTextColor(Color.WHITE)
            status_information_fragment.setTextColor(Color.parseColor("#808080"))
            list_excersice_fragment.setTextColor(Color.parseColor("#808080"))
            val textFragment = HOME_listfood_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        v.list_excersice_fragment.setOnClickListener {
            v.addmenu.isVisible = true
            v.addmenu.setText("เพิ่มรายการกิจกกรม")
            number_add = 2
            v.line1.isVisible = false
            v.line2.isVisible = false
            v.line3.isVisible = true
            val relativeLayout = activity!!.findViewById(R.id.home_user) as RelativeLayout
            relativeLayout.setBackgroundResource(R.drawable.backgroud_status )
            v.list_excersice_fragment.setEnabled(false)
            v.list_food_fragment.setEnabled(true)
            v.status_information_fragment.setEnabled(true)
            Numberfragment = "2"
            list_food_fragment.setTextColor(Color.parseColor("#808080"))
            status_information_fragment.setTextColor(Color.parseColor("#808080"))
            list_excersice_fragment.setTextColor(Color.WHITE)
            val textFragment = HOME_listexcersice_Fragment()
            val bundle = Bundle()
            bundle.putString("time","$timeselect_Fragment")
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
v.addmenu.setOnClickListener {
    if (number_add == 1){
        val back_home_add = "food"
        val intent = Intent(this.requireContext(),selectlistfood_user::class.java)
        intent.putExtra("UID",UID)
        intent.putExtra("back_home_add",back_home_add)
        startActivity(intent)
    }else{
        val back_home_add = "excercise"
        val intent = Intent(this.requireContext(),selectlistexcercise_user::class.java)
        intent.putExtra("UID",UID)
        intent.putExtra("back_home_add",back_home_add)
        startActivity(intent)
    }

}


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
    }
    private fun updateDateInView() {
        val myFormat = "dd-M-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        var data = sdf.format(calendar.getTime())
        timeselect_Fragment=data

        if(Numberfragment == "0"){
            val textFragment = HOME_status_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }else if (Numberfragment == "1"){
            val textFragment = HOME_listfood_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }else{
            val textFragment = HOME_listexcersice_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            bundle.putString("time","$timeselect_Fragment")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HOME_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
