package com.example.foodapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.DataClasses.Item
import com.example.foodapp.DataClasses.Orders
import com.example.foodapp.R

class OrderRecyclerAdapter (val context: Context, var FoodList:ArrayList<Orders>): RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.history_single_item_recycler,parent,false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FoodList.size    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        var orders=FoodList[position]
        holder.restaname.text=orders.restaurant_name
        holder.date.text=orders.order_placed_at
        val items=orders.fooditems
        holder.adapter=
            CartRecyclerAdapter(context, items as ArrayList<Item>)
        holder.layoutManager=LinearLayoutManager(context)
        holder.innerrecycle.adapter=holder.adapter
        holder.innerrecycle.layoutManager=holder.layoutManager
    }
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
      var restaname:TextView=view.findViewById(R.id.restaname)
        var date:TextView=view.findViewById(R.id.date)
        var innerrecycle:RecyclerView=view.findViewById(R.id.innerrecycle)
        lateinit var adapter: CartRecyclerAdapter
        lateinit var layoutManager: LinearLayoutManager
    }
}