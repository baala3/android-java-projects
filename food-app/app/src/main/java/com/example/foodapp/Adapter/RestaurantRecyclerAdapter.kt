package com.example.foodapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.Activity.ItemsActivity
import com.example.foodapp.DataClasses.Restaurant
import com.example.foodapp.Fragment.RestaurantFragment
import com.example.foodapp.R
import com.example.foodapp.database.AddEntity
import com.squareup.picasso.Picasso

class RestaurantRecyclerAdapter(val context: Context,val itemList:ArrayList<Restaurant>):RecyclerView.Adapter<RestaurantRecyclerAdapter.RestaurantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.restaurants_single_row_recycler,parent,false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
return itemList.size }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {

        val text = itemList[position]
        holder.namee.text=text.name
        holder.price.text="$"+text.cost_for_one+"/person"
        holder.rating.text=text.rating
        Picasso.get().load(text.image_url).into(holder.image)
        holder.onclicck.setOnClickListener {
         val intent=Intent(context, ItemsActivity::class.java)
            intent.putExtra("bookid",text.id.toString())
            intent.putExtra("title",text.name)
            context.startActivity(intent)

        }

        val addEntity=AddEntity(
            text.id,
            text.image_url,
            text.name,
            text.cost_for_one,
            text.rating
        )
        val isfav= RestaurantFragment.DBAsynctask(context,addEntity,1).execute().get()
            if(isfav)
            {
                holder.nill.setImageResource(R.drawable.ic_love)

            }
        else
            {
                holder.nill.setImageResource(R.drawable.ic_favorite)
            }

        holder.nill.setOnClickListener {
         if(!RestaurantFragment.DBAsynctask(context,addEntity,1).execute().get())
         {
             val async=
                 RestaurantFragment.DBAsynctask(context,addEntity,2).execute()
             val res=async.get()
             if(res)
             {
                 holder.nill.setImageResource(R.drawable.ic_love)

             }
             else
             {
                 Toast.makeText(context,"some unknown error occured",Toast.LENGTH_SHORT).show()
             }

         }
            else
         {
             val async=
                 RestaurantFragment.DBAsynctask(context,addEntity,3).execute()
             val res=async.get()
             if(res)
             {
                 holder.nill.setImageResource(R.drawable.ic_favorite)
             }
             else
             {
                 Toast.makeText(context,"some unknown error occured",Toast.LENGTH_SHORT).show()
             }
         }
        }
    }


    class RestaurantViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val image:ImageView=view.findViewById(R.id.resitem)
        val namee:TextView=view.findViewById(R.id.restfood)
        val price:TextView=view.findViewById(R.id.restprice)
        val rating:TextView=view.findViewById(R.id.restrating)
        val onclicck:RelativeLayout=view.findViewById(R.id.clicck)
        val nill:ImageView=view.findViewById(R.id.regtlovenill)
    }
}