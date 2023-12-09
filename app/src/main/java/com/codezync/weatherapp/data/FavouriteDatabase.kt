package com.codezync.weatherapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Unit

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [Favourite::class,Unit::class], version = 2, exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

}