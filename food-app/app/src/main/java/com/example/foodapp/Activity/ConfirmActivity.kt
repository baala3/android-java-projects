package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.Adapter.CartRecyclerAdapter
import com.example.foodapp.DataClasses.Item
import com.example.foodapp.R
import com.example.foodapp.database.ItemEntity
import com.example.foodapp.Adapter.retreiveitem
import com.example.foodapp.util.ConnectionManager
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class ConfirmActivity : AppCompatActivity() {
    var cid: String? = "100"
    var cfoodList= arrayListOf<Item>()
    lateinit var ctoolbar:androidx.appcompat.widget.Toolbar
    lateinit var crecyclerAdapter: CartRecyclerAdapter
    lateinit var crecyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var ccart: Button
    lateinit var mtitle:TextView
    var title:String?="bala"
    var obg= listOf<ItemEntity>()
    var book_id:String?="000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        if(intent!=null)
        {
            title=intent.getStringExtra("title")
            book_id=intent.getStringExtra("book_id")
        }
        ccart=findViewById(R.id.mcart)
        ctoolbar=findViewById(R.id.mfftoolbar)
        setSupportActionBar(ctoolbar)
        supportActionBar?.title="My Cart"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mtitle=findViewById(R.id.mtitle)
        mtitle.setText("Ordering from:"+title).toString()

        crecyclerView=findViewById(R.id.mitemrecycler)
var tot:Int=0
        var json=JSONArray()

        obg= retreiveitem(this).execute().get()
        for (i in 0..obg.size-1)
        {
            var tt=JSONObject()
            val iobg=obg.get(i)
            val item= Item(
                iobg.itemid,
                iobg.restname,
                iobg.restprice
            )
            tt.put("food_item_id",iobg.itemid)
            json.put(tt)
            tot += Integer.parseInt(iobg.restprice)
            cfoodList.add(item)
        }
        crecyclerAdapter= CartRecyclerAdapter(this, cfoodList)
        layoutManager= LinearLayoutManager(this)
        crecyclerView.adapter=crecyclerAdapter
        crecyclerView.layoutManager=layoutManager
        var ss=ccart.text.toString()
        ccart.setText(ss+"(Total Rs:${tot})").toString()
        ccart.setOnClickListener {
            val sss=getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)

            val queue = Volley.newRequestQueue(this)
            val url = "http://13.235.250.119/v2/place_order/fetch_result/"
            val jsonparams=JSONObject()
            jsonparams.put("user_id",sss.getString("profid","---"))
            jsonparams.put("restaurant_id",book_id)
            jsonparams.put("total_cost","${tot}")
            jsonparams.put("food", json)

            if (ConnectionManager().checkConnectivity(this)) {

                val jsonrequest = object :
                    JsonObjectRequest(Request.Method.POST, url, jsonparams, Response.Listener {
                        try {

                            val data = it.getJSONObject("data")
                            val success:Boolean= data.getBoolean("success")
                            if(success)
                            {
                                val intent=Intent(this, DoneActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(
                                    this,
                                    "${it}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(
                                this,
                                "${e}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }, Response.ErrorListener {
                        Toast.makeText(
                            this,
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
                Toast.makeText(this, "check your internet connection", Toast.LENGTH_SHORT)
                    .show()
            }


        }

    }
    override fun onBackPressed() {
        val dialog= AlertDialog.Builder(this)
        dialog.setTitle("Confirmation")
        dialog.setMessage("Going back will reset cart items.Do you still want to proceed?")
        dialog.setPositiveButton("Ok"){
                text,listener ->
            super.onBackPressed()

        }
        dialog.setNegativeButton("Cancel"){
                text,listener ->

        }

        dialog.create()
        dialog.show()

    }
}
