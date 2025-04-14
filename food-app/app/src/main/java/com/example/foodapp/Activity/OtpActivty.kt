package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.R
import com.example.foodapp.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class OtpActivty : AppCompatActivity() {
    lateinit var otpotp:EditText
    lateinit var otpnewpassword:EditText
    lateinit var otpcnfnewpass:EditText
    lateinit var otpsubmit:Button
     var formobi:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_activty)
        if(intent!=null)
        {
            formobi=intent.getStringExtra("mobile")
        }

        otpotp=findViewById(R.id.txtotptop)
        otpnewpassword=findViewById(R.id.txtotpnewpassword)
        otpcnfnewpass=findViewById(R.id.txtotpconfirmpassword)
        otpsubmit=findViewById(R.id.btnotpsubmit)
        otpsubmit.setOnClickListener {
                val otp: String = otpotp.text.toString()
                val pass: String = otpnewpassword.text.toString()
                val queue = Volley.newRequestQueue(this@OtpActivty as Context)
                val url = "http://13.235.250.119/v2/reset_password/fetch_result"
                val jsonparams = JSONObject()
                jsonparams.put("mobile_number", formobi)
                jsonparams.put("password",pass)
                jsonparams.put("otp",otp)


                if (ConnectionManager().checkConnectivity(this)) {

                    val jsonrequest = object :
                        JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {
                            try {

                                val data = it.getJSONObject("data")
                                val success:Boolean= data.getBoolean("success")
                                if(success){
                                    Toast.makeText(this@OtpActivty,"Password changed successfully", Toast.LENGTH_SHORT)
                                        .show()
                                    val dialog=AlertDialog.Builder(this@OtpActivty)
                                    dialog.setIcon(R.drawable.ic_tick)
                                    dialog.setTitle("Success")
                                    dialog.setMessage("Password changed successfully")
                                    dialog.setPositiveButton("Ok"){
                                            text,listener ->
                                        val intent = Intent(this@OtpActivty,
                                            LoginActivity::class.java);
                                        startActivity(intent)
                                    }
                                    dialog.create()
                                    dialog.show()
                                }
                                else
                                {
                                    Toast.makeText(this@OtpActivty, "ufffo", Toast.LENGTH_SHORT)
                                        .show()
                                }



                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@OtpActivty,
                                    "${e}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }, Response.ErrorListener {
                            Toast.makeText(
                                this@OtpActivty,
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
                    Toast.makeText(this@OtpActivty, "check your internet connection", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    override fun onPause() {
        super.onPause()
    finish()}

    }


