package com.example.foodapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.foodapp.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Handler().postDelayed(
        {
            val startAct=Intent(this, LoginActivity::class.java)
            startActivity(startAct)
        },2000)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
