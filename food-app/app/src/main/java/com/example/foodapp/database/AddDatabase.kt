package com.example.foodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [AddEntity::class],version = 1)
abstract class AddDatabase:RoomDatabase (){
    abstract fun adddao():AddDao
}