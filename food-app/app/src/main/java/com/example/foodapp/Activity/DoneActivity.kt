package com.example.foodapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.foodapp.R

class DoneActivity : AppCompatActivity() {
lateinit var ook:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done)
        ook=findViewById(R.id.ook)
        ook.setOnClickListener {
            Toast.makeText(this,
                "Thank You", Toast.LENGTH_SHORT)
                .show()
            val intent=Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
