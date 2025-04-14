package com.example.whetherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whetherapp.R;
import com.example.whetherapp.common.common;
import com.example.whetherapp.model.WeatherForecastResult;
import com.squareup.picasso.Picasso;

public  class WeatherForcastAdapter extends RecyclerView.Adapter<WeatherForcastAdapter.ViewHolder> {
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForcastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.single_card_forecast,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image="https://openweathermap.org/img/w/";
        image+=weatherForecastResult.list.get(position).weather.get(0).getIcon()+".png";
        Picasso.get().load(image).into(holder.image);

        holder.date.setText(common.dateTime(weatherForecastResult.list.get(position).dt));
        holder.desc.setText(String.valueOf(weatherForecastResult.list.get(position).weather.get(0).getDescription()));
        holder.temp.setText(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())+"*C");

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView date,desc,temp;
        public ViewHolder(@NonNull View v) {
            super(v);
            image=v.findViewById(R.id.imglogo);
            date=v.findViewById(R.id.txt_datetime);
            desc=v.findViewById(R.id.txtdescription);
            temp=v.findViewById(R.id.txttemp);
        }
    }
}
