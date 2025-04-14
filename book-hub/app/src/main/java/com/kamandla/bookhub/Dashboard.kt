package com.kamandla.bookhub


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
class Dashboard : Fragment() {

    lateinit var recycle:RecyclerView
    lateinit var layout_manager:RecyclerView.LayoutManager
    val bookInfoList = arrayListOf<Book>()

    lateinit var adapter:recycler_view_adapter
    lateinit var relativeRing:RelativeLayout
    lateinit var progress_ring:ProgressBar
var comparator= Comparator<Book> { book1, book2 ->
    if(book1.bookRating.compareTo(book2.bookRating,true)==0)
    {
        book1.bookNAme.compareTo(book2.bookNAme,true)
    }
    else
    {
        book1.bookRating.compareTo(book2.bookRating,true)
    }
}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       val v= inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        recycle=v.findViewById(R.id.recycle_view)
        layout_manager= LinearLayoutManager(activity)

        relativeRing=v.findViewById(R.id.relative_ring)
        progress_ring=v.findViewById(R.id.progress_ring)
        relativeRing.visibility=View.VISIBLE


        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                    val success = it.getBoolean("success")
                    try{  if (success) {
                        relativeRing.visibility=View.GONE
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val boolobject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")
                            )
                            bookInfoList.add(boolobject)
                            adapter = recycler_view_adapter(activity as Context, bookInfoList)
                            recycle.adapter = adapter
                            recycle.layoutManager = layout_manager
                        }
                    } else {
                        Toast.makeText(activity as Context, "some Error occured", Toast.LENGTH_LONG)
                        .show()
                    }}
                    catch (e:JSONException)
                    {
                        Toast.makeText(activity as Context, "some Error occured(Json exception)", Toast.LENGTH_LONG)
                            .show()
                    }

                }, Response.ErrorListener {
                        if(activity!=null) {


                            Toast.makeText(
                                activity as Context,
                                "some Error occured(volley)",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["content-type"] = "application/json"
                        header["token"] = "9bf534118365f1"
                        return header
                    }
                }
            queue.add(jsonObjectRequest)
        }
        else
        {
            val dialpg= AlertDialog.Builder(activity as Context)
            dialpg.setTitle("ERROR")
            dialpg.setMessage("No internet connection")
            dialpg.setPositiveButton("Open Settings"){text,listener->
                val settingIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialpg.setNegativeButton("Exit App"){text,listener->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialpg.create()
            dialpg.show()
        }
        return v

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id= item?.itemId
        if(id==R.id.txtsort)
        {
            Collections.sort(bookInfoList,comparator)
            bookInfoList.reverse()

        }
        adapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }


}
