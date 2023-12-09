package com.codezync.weatherapp.screens.SettingScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.codezync.weatherapp.data.FavouriteDao
import com.codezync.weatherapp.data.FavouriteDatabase
import com.codezync.weatherapp.model.Unit
import com.codezync.weatherapp.navigation.WeatherScreens
import com.codezync.weatherapp.repository.UnitDbRepository
import com.codezync.weatherapp.screens.SearchBar
import com.codezync.weatherapp.widgets.WeatherAppBar
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun SettingScreen(navController: NavController, settingViewModel: SettingViewModel) {

    val unitToggleState = remember {
        mutableStateOf(false)
    }


    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    val choiceFormDB = settingViewModel.unitList.collectAsState().value


    val defaultChoice =
        if (choiceFormDB.isNullOrEmpty()) measurementUnits[0] else choiceFormDB[0].unit


    val choiceState = remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Setting",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Change Units of Measurement")

                IconToggleButton(
                    checked = !unitToggleState.value, onCheckedChange = {
                        unitToggleState.value = !it
                        if (unitToggleState.value) {
                            choiceState.value = "Imperial (F)"
                        } else {
                            choiceState.value = "Metric (C)"
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {

                    Text(text = choiceState.value)

                }

                Button(
                    onClick = {
                        settingViewModel.deleteAllUnits()
                        settingViewModel.insertUnits(Unit(choiceState.value))
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)
                ) {
                    Text(text = "Save")
                }

            }

        }
    }

}

