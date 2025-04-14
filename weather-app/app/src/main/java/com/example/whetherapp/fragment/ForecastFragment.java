package com.example.whetherapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whetherapp.R;
import com.example.whetherapp.adapter.WeatherForcastAdapter;
import com.example.whetherapp.common.IOpenWeatherMap;
import com.example.whetherapp.common.common;
import com.example.whetherapp.model.WeatherForecastResult;
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
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap service;
    RecyclerView recyclerView;
    TextView cityname,geocords;
    static ForecastFragment instancee;
    public static ForecastFragment getInstance()
    {
        if(instancee==null)
            instancee=new ForecastFragment();
        return instancee;
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ForecastFragment() {
        compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        service=retrofit.create(IOpenWeatherMap.class);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
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
      View v=inflater.inflate(R.layout.fragment_forecast, container, false);

      cityname=v.findViewById(R.id.tt_city_name);
      geocords=v.findViewById(R.id.tt_geo_cords);
      recyclerView=v.findViewById(R.id.recycler_forecast);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
      getForecastWeatherInfo();
      return v;
    }

    private void getForecastWeatherInfo() {

        compositeDisposable.add(service.getForecastByLatLon(
                String.valueOf(common.CURRENT_LOCATION.getLatitude()),
                String.valueOf(common.CURRENT_LOCATION.getLongitude()),
                common.APP_ID,
                "metric")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                               @Override
                               public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
displayResults(weatherForecastResult);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_LONG).show();

                            }
                        })




        );


    }

    private void displayResults(WeatherForecastResult weatherForecastResult) {
        cityname.setText(weatherForecastResult.city.getName());
        geocords.setText(weatherForecastResult.city.coord.toString());
        WeatherForcastAdapter adapter=new WeatherForcastAdapter(getActivity(),weatherForecastResult);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }


}
