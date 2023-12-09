package com.codezync.weatherapp.viewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Weather
import com.codezync.weatherapp.repository.FavouriteEntityRepository
import com.codezync.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository,private val favouriteEntitiyRepository: FavouriteEntityRepository) :
    ViewModel() {


   suspend fun getWeatherData(city: String, units: String) : DataOrException<Weather,Boolean,Exception>{

       return repository.getAllWeatherInfo(city,units)
   }
/*
    suspend fun getFavourites() : DataOrException<List<Favourite>,Boolean,Exception>{

        return favouriteEntitiyRepository.getAllFavourite()
    }*/


    suspend fun insertFavourite(favourite : Favourite) : DataOrException<Boolean,Boolean,Exception>{

        return favouriteEntitiyRepository.insert(favourite)
    }

    suspend fun deleteFavourite(favourite : Favourite) : DataOrException<Boolean,Boolean,Exception>{

        return favouriteEntitiyRepository.deleteFavourite(favourite)
    }

    fun addTest(favourite: Favourite) = viewModelScope.launch { favouriteEntitiyRepository.insert(favourite) }



}