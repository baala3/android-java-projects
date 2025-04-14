package com.example.foodapp.Fragment


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodapp.R
import com.example.foodapp.DataClasses.Restaurant
import com.example.foodapp.Adapter.RestaurantRecyclerAdapter
import com.example.foodapp.database.AddDatabase
import com.example.foodapp.database.AddEntity


/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    lateinit var fragrecycler: RecyclerView
    lateinit var frecyclerAdapter: RestaurantRecyclerAdapter
    lateinit var flayoutmanager: LinearLayoutManager
    lateinit var aagam:RelativeLayout
    var fitemList= arrayListOf<Restaurant>()
    var fitem= listOf<AddEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_favorite, container, false)
       aagam=view.findViewById(R.id.aagam)
        aagam.visibility=View.INVISIBLE
        fragrecycler = view.findViewById(R.id.fragrecylerview) as RecyclerView
        flayoutmanager= LinearLayoutManager(context)

            fitem= retreive(activity as Context).execute().get()
        if(fitem.size==0)
        {
            aagam.visibility=View.VISIBLE
        }
        else{


        for (i in 0..fitem.size-1)
        {
            val fff=fitem.get(i)
            var kkk= Restaurant(
                fff.restid,
                fff.restname, fff.restrating,
                fff.restprice,
                fff.restimage
            )
            fitemList.add(kkk)
        }
        if(activity!=null) {
            frecyclerAdapter =
                RestaurantRecyclerAdapter(
                    activity as Context,
                    fitemList
                )
            fragrecycler.adapter = frecyclerAdapter
            fragrecycler.layoutManager = flayoutmanager

        }
            }
        return view
    }
class retreive(val context: Context):AsyncTask<Void,Void,List<AddEntity>>()
{
    override fun doInBackground(vararg params: Void?): List<AddEntity> {
        val db=Room.databaseBuilder(context,AddDatabase::class.java,"add-db").build()
        return db.adddao().getallrest()
    }

}

}
