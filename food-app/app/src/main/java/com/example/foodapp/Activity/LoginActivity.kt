package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.R
import com.example.foodapp.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
lateinit var mobile:EditText
    lateinit var password:EditText
    lateinit var forgotpassword:TextView
    lateinit var login:Button
    lateinit var register:TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences=getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)
        val islogeg=sharedPreferences.getBoolean("islogged",false)
        if(islogeg)
        {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java);
            startActivity(intent)
            finish()
        }




        mobile=findViewById(R.id.txtlogmobile)
        password=findViewById(R.id.txtlogpassword)
        forgotpassword=findViewById(R.id.txtlogforgotpassword)
        register=findViewById(R.id.txtlogregister)
        login=findViewById(R.id.btnlogin)
        var content:SpannableString
        content= SpannableString(forgotpassword.text.toString())
        content.setSpan(UnderlineSpan(),0,content.length,0)
        forgotpassword.setText(content).toString()
        content= SpannableString(register.text.toString())
        content.setSpan(UnderlineSpan(),0,content.length,0)
        register.setText(content).toString()

        login.setOnClickListener {
            login.visibility=View.INVISIBLE

            val mob: String = mobile.text.toString()
            val pass: String = password.text.toString()
            val queue = Volley.newRequestQueue(this@LoginActivity as Context)
            val url = "http://13.235.250.119/v2/login/fetch_result"
            val jsonparams = JSONObject()
            jsonparams.put("mobile_number", mob)
            jsonparams.put("password", pass)


            if (ConnectionManager().checkConnectivity(this)) {

                val jsonrequest = object :
                    JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {


                        try {

                            val data = it.getJSONObject("data")
                            val success:Boolean=data.getBoolean("success") as Boolean
                            if(success){
                                Toast.makeText(this@LoginActivity, "Successfull", Toast.LENGTH_SHORT)
                                    .show()

                                val intent = Intent(this@LoginActivity, HomeActivity::class.java);
                                val response=data.getJSONObject("data")
                                sharedPreferences.edit().putBoolean("islogged",true).apply()
                                sharedPreferences.edit().putString("profname",response.getString("name")).apply()
                                sharedPreferences.edit().putString("profmobile",response.getString("mobile_number")).apply()
                                sharedPreferences.edit().putString("profid",response.getString("user_id")).apply()
                                sharedPreferences.edit().putString("profmail",response.getString("email")).apply()
                                sharedPreferences.edit().putString("profaddress",response.getString("address")).apply()

                                startActivity(intent)

                            }
                            else
                            {
                                login.visibility=View.VISIBLE
                                Toast.makeText(this@LoginActivity, "Enter correct credentials", Toast.LENGTH_SHORT)
                                    .show()
                            }



                        } catch (e: Exception) {
                            login.visibility=View.VISIBLE
                            Toast.makeText(
                                this@LoginActivity,
                                "s${e}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }, Response.ErrorListener {
                        login.visibility=View.VISIBLE
                        Toast.makeText(
                            this@LoginActivity,
                            "${it}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9bf534118365f1"
                        return headers
                    }

                }
                queue.add(jsonrequest)
}
            else
            {
                login.visibility=View.VISIBLE
                Toast.makeText(this@LoginActivity, "check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }

            }
            forgotpassword.setOnClickListener {
                val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java);
                startActivity(intent)
            }
            register.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegistorActivity::class.java);
                startActivity(intent)
            }}

    override fun onPause() {
        super.onPause()
        finish()
    }



    }


