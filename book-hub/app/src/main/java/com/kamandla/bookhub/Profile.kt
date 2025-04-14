package com.kamandla.bookhub


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
class Profile : Fragment() {
lateinit var button: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_profile, container, false)
        button=view.findViewById(R.id.meme)
        button.setOnClickListener {
            Toast.makeText(activity as Context,"You clicked on me!! yayy",Toast.LENGTH_LONG).show()
        }
        return view
    }


}
