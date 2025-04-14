package com.example.mynotes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mynotes.model.Item;
import com.example.mynotes.util.constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    public DatabaseHandler(Context context) {
        super(context, constants.DB_NAME,null, constants.DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+constants.TABLE_NAME+"("
                +constants.KEY_ID+" INTEGER PRIMARY KEY,"
                +constants.KEY_TITLE+" TEXT,"
                +constants.KEY_DESC+" TEXT,"
                +constants.KEY_AMOUNT+" TEXT,"
                +constants.KEY_COLOR+" TEXT,"
                +constants.KEY_DATE+" LONG);";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+constants.TABLE_NAME);
        onCreate(db);

    }
    public  void additem(Item item)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(constants.KEY_TITLE,item.getTitle());
        values.put(constants.KEY_DESC,item.getDesc());
        values.put(constants.KEY_AMOUNT,item.getAmount());
        values.put(constants.KEY_COLOR,item.getColor());
        values.put(constants.KEY_DATE,java.lang.System.currentTimeMillis());
        db.insert(constants.TABLE_NAME,null,values);
        db.close();

    }
    public Item getitem(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(constants.TABLE_NAME,new String[]{
                constants.KEY_ID,constants.KEY_TITLE,constants.KEY_DESC,constants.KEY_AMOUNT,constants.KEY_COLOR,constants.KEY_DATE,
        },constants.KEY_ID+"=?", new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            String dateFormat=DateFormat.getDateInstance().format(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE)));
            Item item=new Item(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))),
                    cursor.getString(cursor.getColumnIndex(constants.KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(constants.KEY_DESC)),
                                    cursor.getString(cursor.getColumnIndex(constants.KEY_AMOUNT)),
                                            cursor.getString(cursor.getColumnIndex(constants.KEY_COLOR)),
                    dateFormat
                            );
            return  item;
        }
        return  null;
    }
    public List<Item> getallitems()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        List<Item> itemList=new ArrayList<>();
        Cursor cursor=db.query(constants.TABLE_NAME,new String[]{
                constants.KEY_ID,constants.KEY_TITLE,constants.KEY_DESC,constants.KEY_AMOUNT,constants.KEY_COLOR,constants.KEY_DATE,
        }, null,null,null,null,constants.KEY_DATE+" DESC");
        if(cursor!=null)
        {
            //cursor.moveToFirst();
            while (cursor.moveToNext())
            {
                String dateFormat=DateFormat.getDateInstance().format(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE)));
                Item item=new Item(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))),
                        cursor.getString(cursor.getColumnIndex(constants.KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(constants.KEY_DESC)),
                        cursor.getString(cursor.getColumnIndex(constants.KEY_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(constants.KEY_COLOR)),
                        dateFormat
                );
                itemList.add(item);
            }
            return  itemList;
        }
        return  null;
    }
    public int updateitem(Item item)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(constants.KEY_ID,item.getId());
        values.put(constants.KEY_TITLE,item.getTitle());
        values.put(constants.KEY_DESC,item.getDesc());
        values.put(constants.KEY_AMOUNT,item.getAmount());
        values.put(constants.KEY_COLOR,item.getColor());
        values.put(constants.KEY_DATE,java.lang.System.currentTimeMillis());
        return db.update(constants.TABLE_NAME,values,constants.KEY_ID+"=?",new String[]{String.valueOf(item.getId())});

    }
    public void deleteitem(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(constants.TABLE_NAME,constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();


    }
    public int getitemcout()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+constants.TABLE_NAME,null);
        return cursor.getCount();
    }
}
