package com.example.foodapp.Adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodapp.DataClasses.Food
import com.example.foodapp.Activity.ItemsActivity
import com.example.foodapp.R
import com.example.foodapp.database.ItemDatabase
import com.example.foodapp.database.ItemEntity

class ItemRecyclerAdapter (val context: Context, var FoodList:ArrayList<Food>): RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view= LayoutInflater.from(parent.context).inflate(R.layout.food_single_item_recycler,parent,false)
            return ItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return FoodList.size    }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val foood=FoodList[position]
            holder.sno.text="${position+1}"
            holder.foodname.text=foood.foodname
            holder.foodprice.text="Rs."+foood.foodcost
            val itemEntity=ItemEntity(
                foood.foodid,
                "${position}",
                foood.foodname,
                foood.foodcost
            )
            val isfav= ItemsActivity.DBAsynctask(context, itemEntity, 1).execute().get()
            if(isfav)
            {
                holder.fooderemove.setText("Remove")
                val favcolor=ContextCompat.getColor(context, R.color.remove)
                holder.fooderemove.setBackgroundColor(favcolor)


            }
            else
            {
                holder.fooderemove.setText("Add")
                val favcolor=ContextCompat.getColor(context, R.color.add)
                holder.fooderemove.setBackgroundColor(favcolor)
            }
            holder.fooderemove.setOnClickListener {
                if(!ItemsActivity.DBAsynctask(context, itemEntity, 1).execute().get())
                {
                    val async=
                        ItemsActivity.DBAsynctask(context, itemEntity, 2).execute()
                    val res=async.get()
                    if(res)
                    {
                        holder.fooderemove.setText("Remove")
                        val favcolor=ContextCompat.getColor(context,
                            R.color.remove
                        )
                        holder.fooderemove.setBackgroundColor(favcolor)

                    }
                    else
                    {
                        Toast.makeText(context,"some unknown error occured", Toast.LENGTH_SHORT).show()
                    }

                }
                else
                {
                    val async=
                        ItemsActivity.DBAsynctask(context, itemEntity, 3).execute()
                    val res=async.get()
                    if(res)
                    {
                        holder.fooderemove.setText("Add")
                        val favcolor=ContextCompat.getColor(context,
                            R.color.add
                        )
                        holder.fooderemove.setBackgroundColor(favcolor)
                    }
                    else
                    {
                        Toast.makeText(context,"some unknown error occured", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)
        {
            var sno: TextView =view.findViewById(R.id.foodno)
            var foodname: TextView =view.findViewById(R.id.foodname)
            var foodprice: TextView =view.findViewById(R.id.foodprice)
            var fooderemove: Button =view.findViewById(R.id.foodadd)
        }
    }
class retreiveitem(val context: Context): AsyncTask<Void, Void, List<ItemEntity>>()
{
    override fun doInBackground(vararg params: Void?): List<ItemEntity> {
        val db= Room.databaseBuilder(context, ItemDatabase::class.java,"item-db").build()
        return db.itemdao().getallitem()
    }

}
