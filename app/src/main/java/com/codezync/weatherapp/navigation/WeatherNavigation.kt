package com.codezync.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codezync.weatherapp.screens.AboutScreen.AboutScreen
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteScreen
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteViewModel
import com.codezync.weatherapp.screens.SearchScreen
import com.codezync.weatherapp.screens.SettingScreen.SettingScreen
import com.codezync.weatherapp.screens.SettingScreen.SettingViewModel
import com.codezync.weatherapp.screens.main.MainScreen
import com.codezync.weatherapp.screens.splash.WeatherSplashScreen
import com.codezync.weatherapp.viewModel.WeatherViewModel

@Composable
fun WeatherNavigation(
    weatherViewModel: WeatherViewModel,
    favouriteViewModel: FavouriteViewModel,
    settingViewModel: SettingViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(
            WeatherScreens.MainScreen.name + "/{weatherLocation}",
            arguments = listOf(navArgument(name = "weatherLocation") { type = NavType.StringType })
        ) {
            MainScreen(
                navController = navController,
                weatherViewModel,
                favouriteViewModel,
                it.arguments?.getString("weatherLocation"),
                settingViewModel = settingViewModel
            )
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }

        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController, favouriteViewModel)
        }

        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController, settingViewModel)
        }
    }


}