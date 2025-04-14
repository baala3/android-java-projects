package com.example.whetherapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.whetherapp.R;
import com.example.whetherapp.common.IOpenWeatherMap;
import com.example.whetherapp.common.common;
import com.example.whetherapp.model.WeatherResult;
import com.example.whetherapp.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment {
    List<String> cities;
    MaterialSearchBar searchBar;

    ImageView imgWeather;
    TextView city,sunRise,sunSet,pressure,humidity,geoCords,temp,descri,dateTime,wind;
    ProgressBar loading;
    LinearLayout pannel;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap iOpenWeatherMap;


    static CityFragment instancee;
    public static CityFragment getInstance()
    {
        if(instancee==null)
            instancee=new CityFragment();
        return instancee;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CityFragment() {
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
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(String param1, String param2) {
        CityFragment fragment = new CityFragment();
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
        View v= inflater.inflate(R.layout.fragment_city, container, false);
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
        searchBar=v.findViewById(R.id.search_bar);
        searchBar.setEnabled(false);
       new  loadcities().execute();


        return v;
    }

    private class loadcities extends SimpleAsyncTask<List<String>> {
        @Override
        protected List<String> doInBackgroundSimple() {
           cities=new ArrayList<>();
            try { StringBuilder stringBuilder=new StringBuilder();
            InputStream inputStream=getResources().openRawResource(R.raw.city_list);
                GZIPInputStream gzipInputStream=new GZIPInputStream(inputStream);
                InputStreamReader inputStreamReader=new InputStreamReader(gzipInputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String readed;
                while ((readed=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(readed);
                    cities= Collections.singletonList(new Gson().toJson(stringBuilder.toString(), new TypeToken<List<String>>() {
                    }.getType()));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cities;
        }

        @Override
        protected void onSuccess(final List<String> citiees) {
            super.onSuccess(citiees);
            searchBar.setEnabled(true);
            searchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
List<String> suggesstions=new ArrayList<>();
for (String search:citiees)
{
    if(search.toLowerCase().contains(searchBar.getText().toLowerCase()))
        suggesstions.add(search);

}
                    searchBar.setLastSuggestions(suggesstions);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {
                    getWhetherInfo(text.toString());
                    searchBar.setLastSuggestions(citiees);
                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });
            searchBar.setLastSuggestions(citiees);
            loading.setVisibility(View.GONE);
          //  pannel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void getWhetherInfo(String toString) {

        compositeDisposable.add(iOpenWeatherMap.getWeatherByCityName(toString,
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
}