package com.codezync.weatherapp.repository

import android.util.Log
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.model.Weather
import com.codezync.weatherapp.network.WeatherService
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherService) {

    private val weatherData = DataOrException<Weather, Boolean, Exception>()


    suspend fun getAllWeatherInfo(cityQuery: String,units : String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getCreditCards(cityQuery, units = units)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }


}