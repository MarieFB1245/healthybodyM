package com.example.healthy_body

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_setting_user.*
import kotlinx.android.synthetic.main.fragment_setting_.*
import kotlinx.android.synthetic.main.fragment_setting_.view.*
import kotlinx.android.synthetic.main.list_food.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SETTING_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    val listset: ArrayList<String> = ArrayList()
    private var myAut = FirebaseAuth.getInstance()
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
        val v = inflater.inflate(R.layout.fragment_setting_, container, false)
        listset.add("ตั้งค่าข้อมูลส่วนตัว")
        listset.add("อาหาร")
        listset.add("กิจกรรม")
        listset.add("อาหารของฉัน")
        listset.add("กิจกรรมของฉัน")

        val UID = arguments!!.getString("UID")
        val UIDs = UID.toString()

        val adapter = GroupAdapter<ViewHolder>()
        listset.forEach {
            val listselect = it
            adapter.add(settext(listselect))

            adapter.setOnItemClickListener { item, view ->
                //var UID :String="GRp37lrFluTK2OhZpUc5dTg0Ofa2"
                val listsetting = item as settext
                var textlistsetting = listsetting.listselectt
                if(textlistsetting.equals("ตั้งค่าข้อมูลส่วนตัว")){
                    val intent = Intent(view.context, edit_information::class.java)
                    intent.putExtra("UID", UIDs)
                    startActivity(intent)
                    getActivity()!!.finish()
                }else if(textlistsetting.equals("อาหาร")){
                    val intent = Intent(view.context, list_edit_food::class.java)
                    intent.putExtra("UID", UIDs)
                    startActivity(intent)
                    getActivity()!!.finish()
                    //  Toast.makeText(this, "อาหาร ยังไม่ได้ทำ", Toast.LENGTH_LONG).show()
                }else if(textlistsetting.equals("กิจกรรม")){
                    val intent = Intent(view.context, list_edit_excercise::class.java)
                    intent.putExtra("UID", UIDs)
                    startActivity(intent)
                    getActivity()!!.finish()
                    //  Toast.makeText(this, "อาหาร ยังไม่ได้ทำ", Toast.LENGTH_LONG).show()
                }else if(textlistsetting.equals("อาหารของฉัน")){
                    val intent = Intent(view.context, list_edit_food_private::class.java)
                    intent.putExtra("UID", UIDs)
                    startActivity(intent)
                    getActivity()!!.finish()
                    //  Toast.makeText(this, "อาหาร ยังไม่ได้ทำ", Toast.LENGTH_LONG).show()
                }
                else {
                    val intent = Intent(view.context, list_edit_excercise_private::class.java)
                    intent.putExtra("UID", UIDs)
                    startActivity(intent)
                    getActivity()!!.finish()

                }





            }
            v.setting_user.adapter = adapter
        }
        v.signout.setOnClickListener {
            val intent = Intent(this.context, Login_user::class.java)
            myAut.signOut()
            startActivity(intent)
            getActivity()!!.finish()
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
        listener = null
    }


    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }
    inner class settext(val listselectt: String) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.list_setting
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {

            viewHolder.itemView.name.text = listselectt


        }



    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SETTING_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
