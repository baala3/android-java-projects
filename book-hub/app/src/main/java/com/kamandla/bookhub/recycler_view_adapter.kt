package com.kamandla.bookhub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class recycler_view_adapter(val context:Context,val array:ArrayList<Book>):RecyclerView.Adapter<recycler_view_adapter.view_holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): view_holder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.recycle_dashboard_single_row,parent,false)
        return view_holder(view)
    }

    override fun getItemCount(): Int {
return array.size
    }

    override fun onBindViewHolder(holder: view_holder, position: Int) {
var book=array[position]
        holder.bookName.text=book.bookNAme
        holder.bookAuthor.text=book.bookAuthor
        holder.bookPrice.text=book.bookPrice
        holder.bookRating.text=book.bookRating
        Picasso.get().load(book.bookImg).error(R.drawable.default_book_cover).into(holder.bookLogo)
        holder.on_click.setOnClickListener()
        {
          val intent=Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }

    }

    class view_holder(view: View):RecyclerView.ViewHolder(view)
    {
       var bookName:TextView=view.findViewById(R.id.txtname_of_book)
        var bookAuthor:TextView=view.findViewById(R.id.txt_author)
        var bookPrice:TextView=view.findViewById(R.id.txt_mrp)
        var bookRating:TextView=view.findViewById(R.id.txtRate)
        var bookLogo:ImageView=view.findViewById(R.id.imgbook)
        var on_click:RelativeLayout=view.findViewById(R.id.iicontent)
    }
}