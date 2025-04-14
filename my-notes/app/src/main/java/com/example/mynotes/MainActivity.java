package com.example.mynotes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mynotes.data.DatabaseHandler;
import com.example.mynotes.model.Item;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Button savebutton;
    EditText notename;
    EditText notedesc;
    EditText noteamount;
    EditText noteColor;
    DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    //    itemList=new ArrayList<>();
         db=new DatabaseHandler(this);

        byPassActivity();
        List<Item> itemList=db.getallitems();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createpopupdialog();
            }
        });
    }

    private void byPassActivity() {
        if (db.getitemcout() > 0) {
            startActivity(new Intent(MainActivity.this, LisActivity.class));
            finish();
        }
    }

    private void createpopupdialog() {
        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup_dialog,null);
        notename=view.findViewById(R.id.txttitle);
        notedesc=view.findViewById(R.id.txtdesc);
        noteamount=view.findViewById(R.id.txtamount);
        noteColor=view.findViewById(R.id.txtcolor);

        savebutton=view.findViewById(R.id.btnsave);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveItem(v);
            }
        });

        builder.setView(view);
        dialog=builder.create();
        dialog.show();
    }

   public void saveItem(View view) {
        //TODO:save the item in db

        Item item=new Item(notename.getText().toString(),
                notedesc.getText().toString(),noteamount.getText().toString(),
                noteColor.getText().toString());
        db.additem(item);
            Snackbar.make(view,"Success",Snackbar.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this, LisActivity.class));

                }
            },1200);


        //TODO: close the popup menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
