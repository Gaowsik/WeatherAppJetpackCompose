package com.codezync.weatherapp.screens.SettingScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Unit
import com.codezync.weatherapp.repository.UnitDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val unitDbRepository: UnitDbRepository) : ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())

    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            unitDbRepository.getUnits().data?.distinctUntilChanged()?.collect(){
                if(it.isNullOrEmpty())
                {
                    Log.d("TAG", "Empty favs")
                }
                else{
                    _unitList.value = it
                    Log.d("TAG", "${_unitList.value}")
                }


            }

        }

    }



    fun insertUnits(unit: Unit) =
        viewModelScope.launch { unitDbRepository.insertUnits(unit) }

    fun deleteAllUnits() = viewModelScope.launch { unitDbRepository.deleteAllUnits() }

    fun updateFavourite(unit: Unit) =
        viewModelScope.launch { unitDbRepository.updateUnit(unit) }

    fun deleteFavourite(unit: Unit) =
        viewModelScope.launch { unitDbRepository.deleteUnit(unit) }

}