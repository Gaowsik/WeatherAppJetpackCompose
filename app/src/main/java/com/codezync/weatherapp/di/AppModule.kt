package com.codezync.weatherapp.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.codezync.weatherapp.data.FavouriteDao
import com.codezync.weatherapp.data.FavouriteDatabase
import com.codezync.weatherapp.network.WeatherService
import com.codezync.weatherapp.repository.FavouriteEntityRepository
import com.codezync.weatherapp.repository.UnitDbRepository
import com.codezync.weatherapp.repository.WeatherRepository
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteScreen
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteViewModel
import com.codezync.weatherapp.screens.SettingScreen.SettingViewModel
import com.codezync.weatherapp.utills.Constants
import com.codezync.weatherapp.viewModel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }


    @Singleton
    @Provides
    fun provideRepository(api: WeatherService) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideViewModel(
        weatherRepository: WeatherRepository,
        entityRepository: FavouriteEntityRepository
    ) =
        WeatherViewModel(weatherRepository, entityRepository)


    @Singleton
    @Provides
    fun provideFavouriteViewModel(
        entityRepository: FavouriteEntityRepository
    ) = FavouriteViewModel(entityRepository)

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): FavouriteDatabase =
        Room.databaseBuilder(
            context,
            FavouriteDatabase::class.java,
            "favourite_dp"
        ).fallbackToDestructiveMigration()
            .build()


    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: FavouriteDatabase): FavouriteDao = noteDatabase.favouriteDao()

    @Singleton
    @Provides
    fun provideEntityRepository(favouriteDao: FavouriteDao): FavouriteEntityRepository =
        FavouriteEntityRepository(favouriteDao)

    @Singleton
    @Provides
    fun provideUnitDbRepository(favouriteDao: FavouriteDao): UnitDbRepository =
        UnitDbRepository(favouriteDao)

    @Singleton
    @Provides
    fun provideSettingViewModel(
        unitDbRepository: UnitDbRepository
    ) = SettingViewModel(unitDbRepository)

}