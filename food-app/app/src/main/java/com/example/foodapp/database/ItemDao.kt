package com.example.foodapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {
        @Insert
        fun insertitem(itemEntity: ItemEntity)
        @Delete
        fun deleteitem(itemEntity: ItemEntity)
        @Query("SELECT * FROM Items")
        fun getallitem():List<ItemEntity>
        @Query("SELECT * FROM Items WHERE itemid=:itemId")
        fun getitembyid(itemId:String):ItemEntity
    }
