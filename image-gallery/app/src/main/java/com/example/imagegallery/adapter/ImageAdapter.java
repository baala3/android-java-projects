package com.example.imagegallery.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagegallery.ImageActivity;
import com.example.imagegallery.model.ImageItem;
import com.example.imagegallery.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    ArrayList<ImageItem> imageItemArrayList;

    public ImageAdapter(ArrayList<ImageItem> imageItemArrayList) {
        this.imageItemArrayList = imageItemArrayList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
final ImageItem imageItem=imageItemArrayList.get(position);
String url=imageItem.getImage();
        Glide.with(holder.context)
                .load(url)
                .placeholder(R.drawable.atg)
                .into(holder.image);
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(holder.context);
                dialog.setContentView(R.layout.image_dialog);
                TextView img_title=dialog.findViewById(R.id.image_title_dialog);
                ImageView image=dialog.findViewById(R.id.image_dialog);
                Button btn_full=dialog.findViewById(R.id.btn_full);
                Button btn_close=dialog.findViewById(R.id.btn_close);

                img_title.setText(imageItem.getTitle());
                Glide.with(holder.context)
                        .load(imageItem.getImage())
                        .placeholder(R.drawable.atg)
                        .centerCrop()
                        .into(image);

                btn_full.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(holder.context,"SUCCESS",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(holder.context, ImageActivity.class);
                        i.putExtra("url",imageItem.getImage());
                        i.putExtra("title",imageItem.getTitle());
                        dialog.dismiss();
                        holder.context.startActivity(i);

                    }
                });
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageItemArrayList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        Context context;
        View itemview;
        //TextView title;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemview=itemView;
            context= itemView.getContext();
            image=itemView.findViewById(R.id.image);
          //  title=itemView.findViewById(R.id.image_title);
        }


    }
}
