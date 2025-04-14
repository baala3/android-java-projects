package com.example.foodapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.DataClasses.Item
import com.example.foodapp.R

class CartRecyclerAdapter (val context: Context, var FoodList:ArrayList<Item>): RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.cart_single_item_recycle,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FoodList.size    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val food=FoodList[position]
        holder.itemcost.text="Rs."+food.itemcost
        holder.itemname.text=food.itemname
    }
    class CartViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var itemname: TextView =view.findViewById(R.id.itemname)
        var itemcost: TextView =view.findViewById(R.id.itemcost)
    }
}