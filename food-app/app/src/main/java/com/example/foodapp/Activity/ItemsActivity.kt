package com.example.foodapp.Activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.Adapter.ItemRecyclerAdapter
import com.example.foodapp.Adapter.retreiveitem
import com.example.foodapp.DataClasses.Food
import com.example.foodapp.R
import com.example.foodapp.database.ItemDatabase
import com.example.foodapp.database.ItemEntity
import com.example.foodapp.util.ConnectionManager
import java.lang.Exception

class ItemsActivity : AppCompatActivity() {
    var id: String? = "100"
    var foodList= arrayListOf<Food>()
    var lll= listOf<ItemEntity>()
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var recyclerAdapter: ItemRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var cart:Button
    lateinit var progress: RelativeLayout
    var obg= listOf<ItemEntity>()
     var title:String?="bala"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        progress=findViewById(R.id.iprogress)
        progress.visibility= View.VISIBLE
        toolbar=findViewById(R.id.fftoolbar)
        cart=findViewById(R.id.cart)
        recyclerView=findViewById(R.id.itemrecycler)
        if (intent != null) {
            id = intent.getStringExtra("bookid")
            title=intent.getStringExtra("title")
        } else {
            finish()
            Toast.makeText(
                this,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (id == "100") {
            finish()
            Toast.makeText(
                this,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title=title
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val queue = Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/${id}"

            if (ConnectionManager().checkConnectivity(this)) {

                val jsonrequest = object :
                    JsonObjectRequest(Request.Method.GET, url,null, com.android.volley.Response.Listener {
                        progress.visibility=View.INVISIBLE
                        try {

                            val data = it.getJSONObject("data")
                            val success: Boolean = data.getBoolean("success")
                            if (success) {
                                val resArray = data.getJSONArray("data")

                                for (i in 0 until resArray.length()) {

                                    val jsonitem = resArray.getJSONObject(i)

                                    val itemobject= Food(
                                        jsonitem.getString("id"),
                                        jsonitem.getString("name"),
                                        jsonitem.getString("cost_for_one"),
                                        jsonitem.getString("restaurant_id")
                                    )

                                    foodList.add(itemobject)

                                    recyclerAdapter =
                                        ItemRecyclerAdapter(
                                            this,
                                            foodList
                                        )

                                    val mlayoutManager = LinearLayoutManager(this@ItemsActivity)
                                    recyclerView.adapter = recyclerAdapter
                                    recyclerView.layoutManager = mlayoutManager
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this,
                                "${e}", Toast.LENGTH_LONG)
                                .show()
                        }

                    }, com.android.volley.Response.ErrorListener {
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
        else{
                Toast.makeText(
                    this,
                    "check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        cart.setOnClickListener {

                obg= retreiveitem(this).execute().get()
                if(obg.size!=0)
                {
                    val intent=Intent(this, ConfirmActivity::class.java)
                    intent.putExtra("title",title)
                    intent.putExtra("book_id",id)
                    startActivity(intent)
                    finish()

                }
            else
                {
                    Toast.makeText(
                        this,
                        "select atleast one item",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }

        lll= retreiveitem(this).execute().get()
        for (i in 0..lll.size-1)
        {
            val async= DBAsynctask(this, lll.get(i), 3).execute().get()
        }


    }
    class DBAsynctask(val context: Context, val itemEntity: ItemEntity, val mode:Int): AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, ItemDatabase::class.java, "item-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val rest:ItemEntity? = db.itemdao().getitembyid(itemEntity.itemid)
                    db.close()
                    return rest != null
                }
                2 -> {
                    db.itemdao().insertitem(itemEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.itemdao().deleteitem(itemEntity)
                    db.close()
                    return true

                }
            }

            return false
        }

    }



    }

