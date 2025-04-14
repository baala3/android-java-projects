package com.example.whetherapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whetherapp.R;
import com.example.whetherapp.common.IOpenWeatherMap;
import com.example.whetherapp.common.common;
import com.example.whetherapp.model.WeatherResult;
import com.example.whetherapp.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayWeatherFragment extends Fragment {
    static TodayWeatherFragment instancee;
    public static TodayWeatherFragment getInstance()
    {
        if(instancee==null)
            instancee=new TodayWeatherFragment();
        return instancee;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView imgWeather;
    TextView city,sunRise,sunSet,pressure,humidity,geoCords,temp,descri,dateTime,wind;
    ProgressBar loading;
    LinearLayout pannel;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap iOpenWeatherMap;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodayWeatherFragment() {
      compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        iOpenWeatherMap=retrofit.create(IOpenWeatherMap.class);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayWeatherFragment newInstance(String param1, String param2) {
        TodayWeatherFragment fragment = new TodayWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_today_weather, container, false);
        city=v.findViewById(R.id.txt_city_name);
          temp=v.findViewById(R.id.txt_temp);
            sunRise=v.findViewById(R.id.txt_sunrise);
             sunSet =v.findViewById(R.id.txt_sunset);
               dateTime =v.findViewById(R.id.txt_date_time);
                imgWeather=v.findViewById(R.id.img_logo);
                  pannel=v.findViewById(R.id.weather_pannel);
                 pressure   =v.findViewById(R.id.txt_pressure);
                    humidity  =v.findViewById(R.id.txt_humidity);
                   wind =v.findViewById(R.id.txt_wind);
                    geoCords=v.findViewById(R.id.txt_geo_cords);
                    loading=v.findViewById(R.id.progress_bar);
                   descri =v.findViewById(R.id.txt_description);
                   getWhetherInfo();

        return v;
    }

    private void getWhetherInfo() {

        compositeDisposable.add(iOpenWeatherMap.getWeatherByLatLon(String.valueOf(common.CURRENT_LOCATION.getLatitude()),
                String.valueOf(common.CURRENT_LOCATION.getLongitude()),
                common.APP_ID,"metric").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult)  {
                        Log.d("ww","LLl");
                        String sr="https://openweathermap.org/img/w/";
                        sr+=weatherResult.getWeather().get(0).getIcon()+".png";
                        Picasso.get().load(sr).error(R.drawable.ic_launcher_background).into(imgWeather);
                        city.setText(weatherResult.getName());
                        descri.setText("weather is: "+weatherResult.getName());
                        temp.setText(String.valueOf(weatherResult.getMain().getTemp())+"*C");
                        dateTime.setText(common.dateTime(weatherResult.getDt()));
                        pressure.setText(String.valueOf(weatherResult.getMain().getPressure())+"hpa");
                        humidity.setText(String.valueOf(weatherResult.getMain().getHumidity())+"%");
                        sunRise.setText(common.tohours(weatherResult.getSys().getSunrise()));
                        sunSet.setText(common.tohours(weatherResult.getSys().getSunset()));
                        geoCords.setText(weatherResult.getCoord().toString());
                        pannel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                 //       Log.d("wwwww","LLl");
                        descri.setText(throwable.getMessage());
pannel.setVisibility(View.VISIBLE);
                    }
                }));

     //   Log.d("wwwwwwwww","LLl");

    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}