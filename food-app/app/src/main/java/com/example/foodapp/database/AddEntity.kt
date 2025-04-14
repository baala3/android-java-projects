package com.example.foodapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurants")
data class AddEntity (
    @PrimaryKey  val restid:String,
    @ColumnInfo(name = "rest_image") val restimage:String,
    @ColumnInfo(name = "rest_name") val restname:String,
    @ColumnInfo(name = "rest_price") val restprice:String,
    @ColumnInfo(name = "rest_rating") val restrating:String

)