package com.kamandla.bookhub


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kamandla.bookhub.database.BookDatabase
import com.kamandla.bookhub.database.BookEntity

class favorites : Fragment() {
   lateinit var recyclerFavorite:RecyclerView
    lateinit var progressbar:ProgressBar
    lateinit var progresslayout:RelativeLayout
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var layoutAdapter:FavoriteRecyclerAdapter
    var dbBookStore= listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_favorites, container, false)
        recyclerFavorite=view.findViewById(R.id.recycle_fav)
       progresslayout=view.findViewById(R.id.favPrgressLayout)
        progressbar=view.findViewById(R.id.favProgressBar)
        layoutManager=GridLayoutManager(activity as Context,2)
        dbBookStore=retrevieData(activity as Context).execute().get()
        if(activity!=null)
        {
            progresslayout.visibility=View.GONE
            layoutAdapter= FavoriteRecyclerAdapter(activity as Context,dbBookStore)
            recyclerFavorite.adapter=layoutAdapter
            recyclerFavorite.layoutManager=layoutManager
        }

        return view
    }
    class retrevieData(val context: Context):AsyncTask<Void,Void,List<BookEntity>>()
    {
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db=Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBooks()
        }

    }


}
