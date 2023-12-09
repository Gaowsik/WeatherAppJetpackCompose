package com.codezync.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codezync.weatherapp.navigation.WeatherNavigation
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteViewModel
import com.codezync.weatherapp.screens.SettingScreen.SettingViewModel
import com.codezync.weatherapp.ui.theme.WeatherAppTheme
import com.codezync.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val weatherViewModel: WeatherViewModel by viewModels()
    val favouriteViewModel: FavouriteViewModel by viewModels()
    val settingViewModel: SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            weatherApp(weatherViewModel, favouriteViewModel, settingViewModel)
        }
    }
}

@Composable
fun weatherApp(
    weatherViewModel: WeatherViewModel,
    favouriteViewModel: FavouriteViewModel,
    settingViewModel: SettingViewModel
) {

    WeatherAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                WeatherNavigation(weatherViewModel, favouriteViewModel, settingViewModel)

            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {

    }
}