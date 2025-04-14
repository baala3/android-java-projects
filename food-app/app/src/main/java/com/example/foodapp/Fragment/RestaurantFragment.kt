package com.example.foodapp.Fragment


import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodapp.DataClasses.Restaurant
import com.example.foodapp.R
import com.example.foodapp.Adapter.RestaurantRecyclerAdapter
import com.example.foodapp.database.AddDatabase
import com.example.foodapp.database.AddEntity
import com.example.foodapp.util.ConnectionManager
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class RestaurantFragment : Fragment() {

    lateinit var progress:RelativeLayout
    lateinit var restrecycler: RecyclerView
    lateinit var recyclerAdapter: RestaurantRecyclerAdapter
    var checkedItem=-1

   var itemList= arrayListOf<Restaurant>()
    var ratingComparator=kotlin.Comparator<Restaurant> { o1, o2 ->
        o1.rating.compareTo(o2.rating,true)
    }
    var costComparator=kotlin.Comparator<Restaurant> { o1, o2 ->
        o1.cost_for_one.compareTo(o2.cost_for_one,true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_restaurant,
            container, false)
        restrecycler = view.findViewById(R.id.restrecylerview) as RecyclerView

        progress=view.findViewById(R.id.fprogress)
        progress.visibility= View.VISIBLE


        setHasOptionsMenu(true)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if (ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonrequest = object :
                JsonObjectRequest(Request.Method.GET, url,
                    null, Response.Listener {
                        progress.visibility=View.INVISIBLE
                    try {

                        val data = it.getJSONObject("data")
                        val success: Boolean = data.getBoolean("success")
                        if (success) {

                            val resArray = data.getJSONArray("data")
                            for (i in 0 until resArray.length()) {
                                val jsonitem = resArray.getJSONObject(i)

                                val itemobject = Restaurant(
                                    jsonitem.getString("id"),
                                    jsonitem.getString("name"),
                                    jsonitem.getString("rating"),
                                    jsonitem.getString("cost_for_one"),
                                    jsonitem.getString("image_url")

                                )
                                itemList.add(itemobject)
                                if (activity != null) {
                                    recyclerAdapter =
                                        RestaurantRecyclerAdapter(
                                            activity
                                                    as Context, itemList
                                        )
                                    val mlayoutManager : LinearLayoutManager
                                    mlayoutManager=LinearLayoutManager(activity)
                                    restrecycler.layoutManager = mlayoutManager
                                    restrecycler.itemAnimator = DefaultItemAnimator()

                                    restrecycler.adapter = recyclerAdapter
                                    restrecycler.setHasFixedSize(true)

                                }

                            }

                        }
                    } catch (e: Exception) {
                        Toast.makeText(activity as Context,
                            "${it}", Toast.LENGTH_SHORT)
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
    class DBAsynctask(val context: Context,val addEntity: AddEntity,val mode:Int): AsyncTask<Void, Void, Boolean>()
    {
        val db=Room.databaseBuilder(context,AddDatabase::class.java,"add-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(mode)
            {
                1->
                {
                    val rest:AddEntity?=db.adddao().getbyid(addEntity.restid)
                    db.close()
                    return rest!=null
                }
                2->
                {
                    db.adddao().insertrest(addEntity)
                    db.close()
                    return true
                }
                3->
                {
                    db.adddao().deleterest(addEntity)
                    db.close()
                    return true

                }
            }
            return false
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       when(item?.itemId) {
           R.id.txtsort ->showDialog(context as Context)
       }
        return super.onOptionsItemSelected(item)
    }

   private fun showDialog(context: Context) {

        val builder: AlertDialog.Builder? = AlertDialog.Builder(context)
        builder?.setTitle("Sort By?")
        builder?.setSingleChoiceItems(R.array.filters, checkedItem ) { _, isChecked ->
            checkedItem = isChecked
        }
        builder?.setPositiveButton("Ok") { _, _ ->

            when (checkedItem) {
                0 -> {
                    Collections.sort(itemList,ratingComparator)
                    itemList.reverse()
                }
                1 -> {
                    Collections.sort(itemList, costComparator)
                    itemList.reverse()
                }
                2 -> {
                    Collections.sort(itemList, costComparator)
                }
            }
           recyclerAdapter.notifyDataSetChanged()
            checkedItem=-1
        }
        builder?.setNegativeButton("Cancel") { _, _ ->

        }
        builder?.create()
        builder?.show()
    }
}


