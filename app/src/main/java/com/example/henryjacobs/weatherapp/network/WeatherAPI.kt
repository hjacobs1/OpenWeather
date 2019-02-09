package com.example.henryjacobs.weatherapp.network

import com.example.henryjacobs.weatherapp.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeatherDetails(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Call<WeatherResult>
}