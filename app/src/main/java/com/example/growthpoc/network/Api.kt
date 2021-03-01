package com.example.growthpoc.network

import com.example.growthpoc.models.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("weather")
    fun getResponse(@Query("q") cityName: String,
                    @Query("appid") apiKey : String,
                    @Query("units") unit : String)
            : Call<WeatherModel>
}