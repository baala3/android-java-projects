package com.example.mynotes.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.LisActivity;
import com.example.mynotes.MainActivity;
import com.example.mynotes.R;
import com.example.mynotes.data.DatabaseHandler;
import com.example.mynotes.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context,List<Item> itemList)
    {
        this.context=context;
        this.itemList=itemList;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_lis_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.row_title.setText(new StringBuilder().append("TITLE: ").append(item.getTitle()).toString());
        holder.row_desc.setText(new StringBuilder().append("DESC: ").append(item.getDesc()).toString());
        holder.row_amount.setText(new StringBuilder().append("AMOUNT: ").append(item.getAmount()).toString());
        holder.row_color.setText(new StringBuilder().append("COLOUR: ").append(item.getColor()).toString());
        holder.row_date.setText(new StringBuilder().append("DATE: ").append(item.getDate()).toString());

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView row_title;
        TextView row_desc;
        TextView row_color;
        TextView row_amount;
        TextView row_date;
        Button roweditbtn;
        Button rowdelbtn;
       private int id;


        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            row_title=itemView.findViewById(R.id.rownotetitle);
            row_desc=itemView.findViewById(R.id.rownotedesc);
            row_amount=itemView.findViewById(R.id.rownoteamount);
            row_color=itemView.findViewById(R.id.rownotecolor);
            row_date=itemView.findViewById(R.id.rownotedate);
            roweditbtn=itemView.findViewById(R.id.roweditbutton);
            rowdelbtn=itemView.findViewById(R.id.rowdeletebutton);
            roweditbtn.setOnClickListener(this);
            rowdelbtn.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.roweditbutton:
                    edititem();
                    break;
                case R.id.rowdeletebutton:
                    deleteid(itemList.get(getAdapterPosition()).getId());
                    break;
            }
        }

        private void edititem() {
            final Item item=itemList.get(getAdapterPosition());
            //Item iteem=itemList.get(getAdapterPosition());
            AlertDialog.Builder builder;
            final AlertDialog dialog;
            builder=new AlertDialog.Builder(context);
            final View view=LayoutInflater.from(context).inflate(R.layout.popup_dialog,null);



            TextView addnotes=view.findViewById(R.id.addnotes);
             final EditText notename=view.findViewById(R.id.txttitle);
           final EditText notedesc=view.findViewById(R.id.txtdesc);
            final EditText  noteamount=view.findViewById(R.id.txtamount);
             final EditText noteColor=view.findViewById(R.id.txtcolor);
          //  final Button


            Button savebutton=view.findViewById(R.id.btnsave);
            savebutton.setText("Update");
            addnotes.setText("Update Notes");
            notename.setText(item.getTitle());
            notedesc.setText(item.getDesc());
            noteamount.setText(item.getAmount());
            noteColor.setText(item.getColor());
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            savebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notenames=notename.getText().toString();
                    String notedescs=notedesc.getText().toString();
                    String noteamounts=noteamount.getText().toString();
                    String noteColors=noteColor.getText().toString();

                    DatabaseHandler dp=new DatabaseHandler(context);
                   // Item item=itemList.get(getAdapterPosition());
                    item.setTitle(notenames);
                    item.setDesc(notedescs);
                    item.setAmount(noteamounts);
                    item.setColor(noteColors);


                    dp.updateitem(item);
                    itemList.set(getAdapterPosition(), item);
                    notifyDataSetChanged();
                    Snackbar.make(v,"Updated!",Snackbar.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();


                        }
                    },1200);
                }
            });












        }

        private void deleteid(final int id) {


            AlertDialog.Builder builder;
            builder=new AlertDialog.Builder(context);
            final AlertDialog dialog;
            View view=LayoutInflater.from(context).inflate(R.layout.confirmpopup,null);
            Button yesbtn=view.findViewById(R.id.btnok);
            Button nobtn=view.findViewById(R.id.btnno);
            builder.setView(view);
            dialog=builder.create();
            dialog.show();
            yesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db=new DatabaseHandler(context);
                    db.deleteitem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();

                }
            });
           nobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });


         //  RecyclerAdapter.this.notify();
        }
    }
}
