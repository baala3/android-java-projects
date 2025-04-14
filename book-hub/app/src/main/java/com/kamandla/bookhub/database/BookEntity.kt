package com.kamandla.bookhub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey var book_id:Int,
    @ColumnInfo(name = "book_name") var bookName:String,
    @ColumnInfo(name = "book_author")  var bookAuthor:String,
    @ColumnInfo(name = "book_price") var bookPrice:String,
    @ColumnInfo(name = "book_rating") var bookRating:String,
    @ColumnInfo(name = "book_des") var bookDes:String,
    @ColumnInfo(name = "book_image") var bookImage:String
)