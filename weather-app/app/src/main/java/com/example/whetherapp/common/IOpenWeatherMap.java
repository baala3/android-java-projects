package com.example.whetherapp.common;

import com.example.whetherapp.model.WeatherForecastResult;
import com.example.whetherapp.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLon(@Query("lat")String lat,
                                                 @Query("lon")String lon,
                                                 @Query("appid")String appId,
                                                 @Query("units")String units);
    @GET("forecast")
    Observable<WeatherForecastResult> getForecastByLatLon(@Query("lat")String lat,
                                                          @Query("lon")String lon,
                                                          @Query("appid")String appId,
                                                          @Query("units")String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName(@Query("q")String name,
                                                 @Query("appid")String appId,
                                                 @Query("units")String units);
}
