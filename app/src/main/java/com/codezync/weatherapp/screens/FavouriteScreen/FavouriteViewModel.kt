package com.codezync.weatherapp.screens.FavouriteScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.repository.FavouriteEntityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteEntityRepository: FavouriteEntityRepository) :
    ViewModel() {

    init {

        viewModelScope.launch(Dispatchers.IO) {
            favouriteEntityRepository.getAllFavourite().data?.distinctUntilChanged()?.collect(){
                if(it.isNullOrEmpty())
                {
                    Log.d("TAG", "Empty favs")
                }
                else{
                    _favList.value = it
                    Log.d("TAG", "${favList.value}")
                }


            }

        }

    }

    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())

    val favList = _favList.asStateFlow()

//    suspend fun insertFavourite(favourite: Favourite): DataOrException<Boolean, Boolean, Exception> {
//
//        return favouriteEntityRepository.insert(favourite)
//    }

/*    suspend fun deleteFavourite(favourite: Favourite): DataOrException<Boolean, Boolean, Exception> {

        return favouriteEntityRepository.deleteFavourite(favourite)
    }*/

    fun insertFavourite(favourite: Favourite) =
        viewModelScope.launch { favouriteEntityRepository.insert(favourite) }

    fun updateFavourite(favourite: Favourite) =
        viewModelScope.launch { favouriteEntityRepository.update(favourite) }

    fun deleteFavourite(favourite: Favourite) =
        viewModelScope.launch { favouriteEntityRepository.deleteFavourite(favourite) }
}