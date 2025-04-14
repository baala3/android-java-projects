package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.R
import com.example.foodapp.util.ConnectionManager
import org.json.JSONObject
import java.lang.Exception

class RegistorActivity : AppCompatActivity() {
    lateinit var txtregname:EditText
    lateinit var txtregemailadd:EditText
    lateinit var txtregmobile:EditText
    lateinit var txtregaddress:EditText
    lateinit var txtregcnfpass:EditText
    lateinit var txtregpassword:EditText
    lateinit var btnlogin:Button
    lateinit var progress: RelativeLayout

lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registor)
        toolbar=findViewById(R.id.txtregtoolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Register yourSelf"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnlogin=findViewById(R.id.btnlogin)

        progress=findViewById(R.id.regprogress)
        progress.visibility= View.INVISIBLE

        txtregpassword=findViewById(R.id.txtregpassword)
        txtregcnfpass=findViewById(R.id.txtregconfpassword)
        txtregaddress=findViewById(R.id.txtregaddress)
        txtregmobile=findViewById(R.id.txtregmobile)
        txtregemailadd=findViewById(R.id.txtregemailaddress)
        txtregname=findViewById(R.id.txtregname)
        btnlogin.setOnClickListener {
            progress.visibility= View.VISIBLE

                val name: String = txtregname.text.toString()
                val mobile: String = txtregmobile.text.toString()
            val email: String = txtregemailadd.text.toString()
            val address: String = txtregaddress.text.toString()
            val passwa: String = txtregpassword.text.toString()
            val cnfpass: String = txtregcnfpass.text.toString()

                val queue = Volley.newRequestQueue(this@RegistorActivity as Context)
                val url = "http://13.235.250.119/v2/register/fetch_result"
                val jsonparams = JSONObject()
                jsonparams.put("name",name)
            jsonparams.put("mobile_number", mobile)
                jsonparams.put("password",passwa)
            jsonparams.put("address",address )
            jsonparams.put("email",email )


                if (ConnectionManager().checkConnectivity(this)) {

                    val jsonrequest = object :
                        JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {
                            progress.visibility= View.INVISIBLE
                            try {

                                val data = it.getJSONObject("data")
                                val success:Boolean= data.getBoolean("success")
                                if(success){
                                    Toast.makeText(this@RegistorActivity, "Registration successfull!!", Toast.LENGTH_SHORT)
                                        .show()
                                    val response=data.getJSONObject("data")
                                    val sharedPreferences=getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)

                                    sharedPreferences.edit().putBoolean("islogged",true).apply()
                                    sharedPreferences.edit().putString("profid",response.getString("user_id")).apply()
                                    sharedPreferences.edit().putString("profname",response.getString("name")).apply()
                                    sharedPreferences.edit().putString("profmail",response.getString("email")).apply()
                                    sharedPreferences.edit().putString("profmobile",response.getString("mobile_number")).apply()
                                    sharedPreferences.edit().putString("profaddress",response.getString("address")).apply()
                                    val intent = Intent(this@RegistorActivity, HomeActivity::class.java);
                                    startActivity(intent)

                                }
                                else
                                {
                                    Toast.makeText(this@RegistorActivity, "Enter correct credentials", Toast.LENGTH_SHORT)
                                        .show()
                                }



                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@RegistorActivity,
                                    "${e}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }, Response.ErrorListener {
                            Toast.makeText(
                                this@RegistorActivity,
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
                    Toast.makeText(this@RegistorActivity, "check your internet connection", Toast.LENGTH_SHORT)
                        .show()
                }


        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
