package com.example.healthy_body

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthy_body.calculate.selectdata_totalkcal
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home_listfood_.*
import kotlinx.android.synthetic.main.fragment_home_listfood_.view.*
import kotlinx.android.synthetic.main.listselect_edit_food.view.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HOME_listfood_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        val v = inflater.inflate(R.layout.fragment_home_listfood_, container, false)
        val UID = arguments!!.getString("UID")
        myRef = FirebaseDatabase.getInstance().reference

        val progest  = ProgressDialog(this@HOME_listfood_Fragment.context,R.style.MyTheme)
        progest.setCancelable(false)
        progest.show()
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())


        var adapters = GroupAdapter<ViewHolder>()
        //mRecycleVeiew.adapter = adapters

        myRef = FirebaseDatabase.getInstance().getReference("SELECTFOOD").child("${UID.toString()}").child("$currentDate")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("p0", dataSnapshot.toString())

                if(dataSnapshot.exists()){
                    dataSnapshot.children.forEach {
                        Log.d("DataSnapshot", it.toString())
                        val listfood = it.getValue(dataselectfood::class.java)
                        Log.d("text", listfood.toString())
                        if (listfood != null) {
                            adapters.add(Foodlist(listfood))
                           listcout= listcout + 1

                        }
                        v.amtwo.setText(listcout.toString())
                        v.fragListRecycleVeiew.adapter = adapters


                    }
                    selectdata_totalkcal(UID.toString()).getdatatotal{ excercise, food ->
                        var Food = food.toInt()
                        var  Workout = excercise.toInt()
                        v.caltwo.setText(Food.toString())
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


    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }
    class Foodlist(val listfood: dataselectfood) : Item<ViewHolder>() {
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.name.text= listfood.nameFoodShowB
            viewHolder.itemView.kcal.text = listfood.kcalfoodShowB
            Log.d("viewHolder", "${viewHolder}")
        }

        override fun getLayout(): Int {
            return R.layout.listfood_show_fragment

        }


    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HOME_listfood_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
