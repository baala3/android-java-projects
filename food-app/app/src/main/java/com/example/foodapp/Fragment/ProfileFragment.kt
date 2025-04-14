package com.example.foodapp.Fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodapp.R


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
   lateinit var profname:TextView
    lateinit var profnumber:TextView
    lateinit var profmail:TextView
    lateinit var profaddress:TextView
    lateinit var  sharedPreferences:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        profname=view.findViewById(R.id.profname)
        profnumber=view.findViewById(R.id.profnumber)
        profmail=view.findViewById(R.id.profmail)
        profaddress=view.findViewById(R.id.profaddress)
        sharedPreferences=
            activity!!.getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)
        profname.setText(sharedPreferences.getString("profname","---")).toString()
        profnumber.setText(sharedPreferences.getString("profmobile","---")).toString()
        profaddress.setText(sharedPreferences.getString("profaddress","---")).toString()
        profmail.setText(sharedPreferences.getString("profmail","---")).toString()
   return view
    }


}
