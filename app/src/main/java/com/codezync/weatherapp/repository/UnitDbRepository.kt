package com.codezync.weatherapp.repository

import android.util.Log
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.data.FavouriteDao
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnitDbRepository @Inject constructor(private val favouriteDao: FavouriteDao) {


    fun getUnits() : DataOrException<Flow<List<Unit>>, Boolean, Exception> {

        val response = try {
            favouriteDao.getUnits()

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }



    suspend fun insertUnits(unit: Unit) : DataOrException<Boolean, Boolean, Exception> {
        val response = try {
            favouriteDao.insertUnit(unit)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }

    suspend fun updateUnit(unit: Unit) : DataOrException<Boolean, Boolean, Exception> {
        val response = try {
            favouriteDao.updateUnit(unit)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }


    suspend fun deleteAllUnits() : DataOrException<Boolean, Boolean, Exception> {
        val response = try {
            favouriteDao.deleteAllUnits()

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }

    suspend fun deleteUnit(unit: Unit) : DataOrException<Boolean, Boolean, Exception> {
        val response = try {
            favouriteDao.deleteUnit(unit)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)



    }

    suspend fun getFavById(City: String) : DataOrException<Favourite, Boolean, Exception> {
        val response = try {
            favouriteDao.getFavouriteByCity(City)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)


    }


















}