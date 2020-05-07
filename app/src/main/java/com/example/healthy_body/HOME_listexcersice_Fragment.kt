package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthy_body.calculate.dateselect_totalvalue
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.example.healthy_body.model.modellistexcercise
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_edit_food.*
import kotlinx.android.synthetic.main.activity_selectlistfood_user.*
import kotlinx.android.synthetic.main.fragment_home_listexcersice_.*
import kotlinx.android.synthetic.main.fragment_home_listfood_.view.*
import kotlinx.android.synthetic.main.list_food.view.*
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HOME_listexcersice_Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var myRef: DatabaseReference
    var listcout :Int = 0

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
        val v = inflater.inflate(R.layout.fragment_home_listexcersice_, container, false)
        val UID = arguments!!.getString("UID")
         myRef = FirebaseDatabase.getInstance().reference
        val Timeargument = arguments!!.getString("time")
        Log.e("timeagument_listexcercise",Timeargument)
        val progest  = ProgressDialog(this.context,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()



        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        var adapters = GroupAdapter<ViewHolder>()
        //mRecycleVeiew.adapter = adapters
        myRef = FirebaseDatabase.getInstance().getReference("SELECTEXCERCISE").child("${UID.toString()}").child("${Timeargument.toString()}")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("p0", dataSnapshot.toString())

                if(dataSnapshot.exists()){
                    dataSnapshot.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val excercise = it.getValue(dataselectexcercise::class.java)
                        if (excercise != null) {
                            adapters.add(Excerciselist(excercise))
                            listcout= listcout + 1

                        }
                        v.amtwo.setText(listcout.toString())
                        v.fragListRecycleVeiew.adapter = adapters


                    }

                    dateselect_totalvalue(UID.toString(),Timeargument.toString()).callvalue{ excercise, food ->
                        var Food = food.toInt()
                        var  Workout = excercise.toInt()
                        v.caltwo.setText(Workout.toString())
                        progest.cancel()
                    }


                }else{
                    progest.cancel()
                    Log.e("listfood","Don't have")
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

    inner class Excerciselist(val excercise: dataselectexcercise) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text = excercise.nameExcerciseShowB
            viewHolder.itemView.kcal.text = excercise.kcalExcerciseShowB
            Log.d("viewHolder", "${viewHolder}")


        }

        override fun getLayout(): Int {
            return R.layout.listexcercise_show_fragment
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HOME_listexcersice_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
