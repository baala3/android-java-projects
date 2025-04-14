package com.example.whetherapp.common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class common {
    public static final String APP_ID="77bc196483e1592dc240d28266533685";
    public  static Location CURRENT_LOCATION=null;

    public static String dateTime(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted=simpleDateFormat.format(date);
        return  formatted;
    }

    public static String tohours(long sunrise) {
        Date date=new Date(sunrise*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        String formatted=simpleDateFormat.format(date);
        return  formatted;
    }
}
