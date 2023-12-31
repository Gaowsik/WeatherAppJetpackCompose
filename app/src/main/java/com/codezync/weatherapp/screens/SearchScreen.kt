package com.codezync.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codezync.weatherapp.navigation.WeatherScreens
import com.codezync.weatherapp.screens.main.MainContent
import com.codezync.weatherapp.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {

        WeatherAppBar(
            title = "Search",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
        ) {
            navController.popBackStack()
        }

    }) {

        androidx.compose.material.Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(modifier =Modifier.fillMaxWidth().padding(16.dp).align(Alignment.CenterHorizontally),navController) { city ->
                    navController.navigate(WeatherScreens.MainScreen.name+"/$city")



                }


            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,navController: NavController,onSearch: (String) -> Unit) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val validate = remember(searchQueryState.value){

        searchQueryState.value.trim().isNotEmpty()


    }
    Column {
        CommonTextField(valueState = searchQueryState,
            placeholder = "Seattle",
            onAction = KeyboardActions {
                if(!validate){
                    return@KeyboardActions
                }
                else{
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value=""
                    keyboardController?.hide()
                }
            })
    }


}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {
            Text(
                text = placeholder
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue, cursorColor = Color.Black
        ), shape = RoundedCornerShape(15.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )

}
