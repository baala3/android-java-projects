package com.example.foodapp.DataClasses

import com.example.foodapp.DataClasses.Item

data class Orders (
    var order_id:String,
    var restaurant_name:String,
    var total_cost:String,
    var order_placed_at:String,
    var fooditems:List<Item>
)