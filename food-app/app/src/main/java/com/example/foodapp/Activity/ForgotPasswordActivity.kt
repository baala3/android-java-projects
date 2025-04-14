package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
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

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var formobile:EditText
    lateinit var foremail:EditText
    lateinit var forbutton:Button
    lateinit var progress: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        progress=findViewById(R.id.fffprogress)
        progress.visibility= View.INVISIBLE
        formobile=findViewById(R.id.txtforgotmobile)
        foremail=findViewById(R.id.txtforgotemail)
        forbutton=findViewById(R.id.btnforgotnext)
        forbutton.setOnClickListener {
            progress.visibility= View.VISIBLE

                val formob: String = formobile.text.toString()
                val formail: String = foremail.text.toString()
                val queue = Volley.newRequestQueue(this@ForgotPasswordActivity as Context)
                val url = "http://13.235.250.119/v2/forgot_password/fetch_result"
                val jsonparams = JSONObject()
                jsonparams.put("mobile_number", formob)
                jsonparams.put("email", formail)


                if (ConnectionManager().checkConnectivity(this)) {

                    val jsonrequest = object :
                        JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {
                            try {

                                val data = it.getJSONObject("data")
                                val success:Boolean= data.getBoolean("success")
                                if(success){
                                    Toast.makeText(this@ForgotPasswordActivity, "verify your credentials", Toast.LENGTH_SHORT)
                                        .show()



                                    if(data.getBoolean("first_try"))
                                    {
                                        progress.visibility=View.INVISIBLE
                                        val dialog=AlertDialog.Builder(this@ForgotPasswordActivity)
                                        dialog.setTitle("Information")
                                        dialog.setMessage("OTP has been sent to your email address! verify it")
                                        dialog.setPositiveButton("Ok"){
                                            text,listener ->
                                            val intent = Intent(this@ForgotPasswordActivity, OtpActivty::class.java);
                                            intent.putExtra("mobile",formob)
                                            startActivity(intent)
                                        }
                                        dialog.setNegativeButton("Cancel"){
                                                text,listener ->
                                            val intent = Intent(this@ForgotPasswordActivity,
                                                LoginActivity::class.java);
                                            intent.putExtra("mobile",formob)
                                            startActivity(intent)
                                        }
                                        dialog.create()
                                        dialog.show()
                                    }
                                    else
                                    {
                                        progress.visibility=View.INVISIBLE
                                        val dialog=AlertDialog.Builder(this@ForgotPasswordActivity)
                                        dialog.setTitle("Information")
                                        dialog.setMessage("Please refer to previously sent email")
                                        dialog.setPositiveButton("Ok"){
                                                text,listener ->
                                            val intent = Intent(this@ForgotPasswordActivity, OtpActivty::class.java);
                                            intent.putExtra("mobile",formob)
                                            startActivity(intent)
                                        }
                                        dialog.setNegativeButton("Cancel"){
                                                text,listener ->
                                            val intent = Intent(this@ForgotPasswordActivity,
                                                LoginActivity::class.java);
                                            intent.putExtra("mobile",formob)
                                            startActivity(intent)
                                        }

                                        dialog.create()
                                        dialog.show()
                                    }




                                }
                                else
                                {
                                    Toast.makeText(this@ForgotPasswordActivity, "Enter the correct credentials", Toast.LENGTH_SHORT)
                                        .show()
                                }



                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    "${e}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }, Response.ErrorListener {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
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
                    Toast.makeText(this@ForgotPasswordActivity, "check your internet connectivity", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

