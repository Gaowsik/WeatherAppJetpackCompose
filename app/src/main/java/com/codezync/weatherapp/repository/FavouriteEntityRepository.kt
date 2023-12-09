package com.codezync.weatherapp.repository

import android.content.res.Resources
import android.provider.ContactsContract.Data
import android.util.Log
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.data.FavouriteDao
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteEntityRepository @Inject constructor(private val favouriteDao: FavouriteDao)
{

     fun getAllFavourite() : DataOrException<Flow<List<Favourite>>, Boolean, Exception> {

        val response = try {
            favouriteDao.getNotes()

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }



    suspend fun insert(favourite: Favourite) :DataOrException<Boolean, Boolean, Exception>{
        val response = try {
            favouriteDao.insert(favourite)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }

    suspend fun update(favourite: Favourite) :DataOrException<Boolean, Boolean, Exception>{
        val response = try {
            favouriteDao.update(favourite)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }


    suspend fun deleteAll() :DataOrException<Boolean, Boolean, Exception>{
        val response = try {
            favouriteDao.deleteAllFavourites()

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)


    }

    suspend fun deleteFavourite(favourite: Favourite) : DataOrException<Boolean,Boolean,Exception>{
        val response = try {
            favouriteDao.deleteFavourite(favourite)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = true)



    }

    suspend fun getFavById(City: String) :DataOrException<Favourite, Boolean, Exception>{
        val response = try {
            favouriteDao.getFavouriteByCity(City)

        } catch (exception: Exception) {
            return DataOrException(e = exception)
        }

        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)


    }


    }


