package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mynotes.Adapters.RecyclerAdapter;
import com.example.mynotes.data.DatabaseHandler;
import com.example.mynotes.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LisActivity extends AppCompatActivity {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    private List<Item> itemList;
    public DatabaseHandler db;
    private FloatingActionButton fab;
    private AlertDialog dialog;
    Button savebutton;
    EditText notename;
    EditText notedesc;
    EditText noteamount;
    EditText noteColor;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis);
        recyclerView=findViewById(R.id.recyler);
        fab=findViewById(R.id.lisfab);



        db=new DatabaseHandler(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList=new ArrayList<>();
        itemList=db.getallitems();


        recyclerAdapter=new RecyclerAdapter(this,itemList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpopupDialog();

            }
        });
    }

    private void createpopupDialog() {
builder=new AlertDialog.Builder(this);
View view=getLayoutInflater().inflate(R.layout.popup_dialog,null);
        notename=view.findViewById(R.id.txttitle);
        notedesc=view.findViewById(R.id.txtdesc);
        noteamount=view.findViewById(R.id.txtamount);
        noteColor=view.findViewById(R.id.txtcolor);
        savebutton=view.findViewById(R.id.btnsave);

builder.setView(view);
dialog=builder.create();
dialog.show();
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveItem(v);
            }
        });
    }

    public void saveItem(View view) {
        Item item=new Item(notename.getText().toString(),
                notedesc.getText().toString(),noteamount.getText().toString(),
                noteColor.getText().toString());
        db.additem(item);

        Snackbar.make(view,"Success",Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                startActivity(new Intent(LisActivity.this, LisActivity.class));

                finish();

            }
        },1200);
    }
}
