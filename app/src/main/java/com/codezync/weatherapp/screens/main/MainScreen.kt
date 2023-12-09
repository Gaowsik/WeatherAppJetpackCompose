package com.codezync.weatherapp.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codezync.weatherapp.data.DataOrException
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.model.Weather
import com.codezync.weatherapp.navigation.WeatherScreens
import com.codezync.weatherapp.screens.FavouriteScreen.FavouriteViewModel
import com.codezync.weatherapp.screens.SettingScreen.SettingViewModel
import com.codezync.weatherapp.utills.toCustomDateString
import com.codezync.weatherapp.viewModel.WeatherViewModel
import com.codezync.weatherapp.widgets.HumidyWindPresserRow
import com.codezync.weatherapp.widgets.SunsetSunriseRow
import com.codezync.weatherapp.widgets.WeatherAppBar
import com.codezync.weatherapp.widgets.WeatherListItem
import com.codezync.weatherapp.widgets.circleContent

@Composable
fun MainScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    favouriteViewModel: FavouriteViewModel,
    weatherLocation: String? = "lisbon",
    settingViewModel : SettingViewModel
) {

    val unitFromDb = settingViewModel.unitList.collectAsState().value
    var unit = remember {
        mutableStateOf("imperial")
    }

    val isImperial = remember {
        mutableStateOf(false)
    }


    if(!unitFromDb.isNullOrEmpty()){
        unit.value = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial.value = unit.value == "imperial"

        val context = LocalContext.current

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {

            value = weatherViewModel.getWeatherData(weatherLocation!!,units = unit.value)

        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, favouriteViewModel,isImperial, navController) {
                weatherViewModel.addTest(
                    Favourite(
                        weatherData.data!!.city.name,
                        weatherData.data!!.city.country
                    )
                )
                Toast.makeText(context, "added successfull", Toast.LENGTH_SHORT).show()
            }

        }

    }



}

@Composable
fun MainScaffold(
    weather: Weather,
    favouriteViewModel: FavouriteViewModel,
    isImperial : MutableState<Boolean>,
    navController: NavController,
    isFavourite: (Boolean) -> Unit

) {
    val isAlreadyFacList = favouriteViewModel.favList.collectAsState().value.filter { item ->
        item.city == weather.city.name

    }
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            isAlreadyFacList,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            onFavouriteClicked = { isFavourite.invoke(it) }
        ) {


        }
    }) {

        MainContent(data = weather,isImperial)

    }

}


@Composable
fun MainContent(data: Weather, isImperial: MutableState<Boolean>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = data.list[0].dt.toLong().toCustomDateString(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        circleContent(data)

        HumidyWindPresserRow(data.list[0],isImperial.value)

        Divider()
        SunsetSunriseRow(data.list[0])

        Text(
            text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(modifier = Modifier.padding(top = 4.dp), contentPadding = PaddingValues(1.dp)) {
            items(data.cnt) { position ->
                data.list[position].let { item ->
                    WeatherListItem(item)
                }

            }
        }

    }

}









