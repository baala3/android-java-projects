package com.example.foodapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AddDao {
    @Insert
    fun insertrest(addEntity: AddEntity)
    @Delete
    fun deleterest(addEntity: AddEntity)
    @Query("SELECT * FROM Restaurants")
    fun getallrest():List<AddEntity>
    @Query("SELECT * FROM Restaurants WHERE restid=:restId")
    fun getbyid(restId:String):AddEntity
}
