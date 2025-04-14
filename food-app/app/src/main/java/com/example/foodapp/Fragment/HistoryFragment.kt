package com.example.foodapp.Fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.DataClasses.Item
import com.example.foodapp.Adapter.OrderRecyclerAdapter
import com.example.foodapp.DataClasses.Orders
import com.example.foodapp.R
import com.example.foodapp.util.ConnectionManager
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {
lateinit var hirecyclerview:RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    lateinit var progress: RelativeLayout

    lateinit var adapter: OrderRecyclerAdapter
     var list= arrayListOf<Orders>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view=inflater.inflate(R.layout.fragment_history, container, false)
        val sss=this.activity!!.getSharedPreferences(getString(R.string.preference_login_fine),Context.MODE_PRIVATE)
         val id=sss.getString("profid","---")
        progress=view.findViewById(R.id.hprogress)
        progress.visibility= View.VISIBLE
hirecyclerview=view.findViewById(R.id.hirecycler)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/orders/fetch_result/${id}"

        if (ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonrequest = object :
                JsonObjectRequest(Request.Method.GET, url,
                    null, Response.Listener {

                        progress.visibility=View.INVISIBLE
                        try {

                            val data = it.getJSONObject("data")
                            val success: Boolean = data.getBoolean("success")
                            if (success) {

                                val foodArray = data.getJSONArray("data")
                                for (i in 0..foodArray.length()-1) {
                                    val jsonitem = foodArray.getJSONObject(i)
                                    val iteem = arrayListOf<Item>()
                                    val items = jsonitem.getJSONArray("food_items")
                                    for (j in 0..items.length() - 1) {
                                        val each = items.getJSONObject(j)
                                        var fooditems = Item(
                                            each.getString("food_item_id"),
                                            each.getString("name"),
                                            each.getString("cost")
                                        )
                                        iteem.add(fooditems)
                                    }
                                    val itemobject = Orders(
                                        jsonitem.getString("order_id"),
                                        jsonitem.getString("restaurant_name"),
                                        jsonitem.getString("total_cost"),
                                        jsonitem.getString("order_placed_at"),
                                        iteem
                                    )
                                    list.add(itemobject)

                                }

                                adapter= OrderRecyclerAdapter(
                                    activity as Context,
                                    list
                                )
                                layoutManager= LinearLayoutManager(context)
                                hirecyclerview.adapter=adapter
                                hirecyclerview.layoutManager=layoutManager

                                }

                        } catch (e: Exception) {
                            Toast.makeText(activity as Context,
                                "${e}", Toast.LENGTH_LONG)
                                .show()
                        }

                    }, Response.ErrorListener {
                        Toast.makeText(
                            activity as Context,
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


        } else {
            val builder = AlertDialog.Builder(activity as Context)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found." +
                    " Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            builder.create()
            builder.show()
        }


 return view
    }


}
