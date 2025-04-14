package com.kamandla.bookhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SlashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)
        Handler().postDelayed({
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        },4000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
