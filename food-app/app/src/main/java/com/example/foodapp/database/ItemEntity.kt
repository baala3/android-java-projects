package com.example.foodapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Items")
data class ItemEntity (
    @PrimaryKey val itemid:String,
    @ColumnInfo(name = "item_no") val resno:String,
    @ColumnInfo(name = "item_name") val restname:String,
    @ColumnInfo(name = "item_price") val restprice:String

)