package com.codezync.weatherapp.network

import com.codezync.weatherapp.model.Weather
import com.codezync.weatherapp.utills.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherService {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getCreditCards(
    @Query("q") query: String,
    @Query("appid") appid: String = Constants.API_URL,
    @Query("units") units: String = "imperial"

    ): Weather
}
