package com.example.healthy_body

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlinx.android.synthetic.main.fragment_home_.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HOME_Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        val v = inflater.inflate(R.layout.fragment_home_, container, false)
        val UID = arguments!!.getString("UID")
        v.buttonRe.setOnClickListener {
            val intent = Intent(this.context,dashboard_user::class.java)
            intent.putExtra("UID",UID)
            startActivity(intent)
            getActivity()!!.finish()
        }


        v.list_food_fragment.setTextColor(Color.BLACK)
        v.status_information_fragment.setTextColor(Color.WHITE)
        v.list_excersice_fragment.setTextColor(Color.BLACK)
        val textFragment = HOME_status_Fragment()
        val bundle = Bundle()
        bundle.putString("UID","$UID")
        textFragment.setArguments(bundle)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,textFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        v.status_information_fragment.setEnabled(false)



        v.status_information_fragment.setOnClickListener {
            v.status_information_fragment.setEnabled(false)
            v.list_food_fragment.setEnabled(true)
            v.list_excersice_fragment.setEnabled(true)

            list_food_fragment.setTextColor(Color.BLACK)
            status_information_fragment.setTextColor(Color.WHITE)
            list_excersice_fragment.setTextColor(Color.BLACK)
            val textFragment = HOME_status_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
        v.list_food_fragment.setOnClickListener {
            v.list_food_fragment.setEnabled(false)
            v.status_information_fragment.setEnabled(true)
            v.list_excersice_fragment.setEnabled(true)

            list_food_fragment.setTextColor(Color.WHITE)
            status_information_fragment.setTextColor(Color.BLACK)
            list_excersice_fragment.setTextColor(Color.BLACK)
            val textFragment = HOME_listfood_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        v.list_excersice_fragment.setOnClickListener {
            v.list_excersice_fragment.setEnabled(false)
            v.list_food_fragment.setEnabled(true)
            v.status_information_fragment.setEnabled(true)

            list_food_fragment.setTextColor(Color.BLACK)
            status_information_fragment.setTextColor(Color.BLACK)
            list_excersice_fragment.setTextColor(Color.WHITE)
            val textFragment = HOME_listexcersice_Fragment()
            val bundle = Bundle()
            bundle.putString("UID","$UID")
            textFragment.setArguments(bundle)
            val transaction = childFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.fragment_container,textFragment)
            transaction.addToBackStack(null)
            transaction.commit()
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
