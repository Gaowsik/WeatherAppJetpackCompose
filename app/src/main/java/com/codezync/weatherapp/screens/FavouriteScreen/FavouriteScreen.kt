package com.codezync.weatherapp.screens.FavouriteScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.navigation.WeatherScreens
import com.codezync.weatherapp.screens.SearchBar
import com.codezync.weatherapp.widgets.WeatherAppBar

@Composable
fun FavouriteScreen(navController: NavController, favouriteViewModel: FavouriteViewModel) {

    val favouriteList = favouriteViewModel.favList.collectAsState().value
    Scaffold(topBar = {

        WeatherAppBar(
            title = "Favourite",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }

    }) {

        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn {

                    items(favouriteList) { favourite ->
                        FavouriteRow(favouriteViewModel = favouriteViewModel, favourite = favourite) {
                            navController.navigate(WeatherScreens.MainScreen.name+"/${it.city}")
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun FavouriteRow(
    modifier: Modifier = Modifier,favouriteViewModel: FavouriteViewModel, favourite: Favourite, onFavouriteclicked: (Favourite) -> Unit
) {
    Surface(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(bottomEnd = 33.dp, topStart = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(), color = Color(0xFFDFE6EB), elevation = 6.dp
    ) {

        Row(
            modifier
                .clickable { onFavouriteclicked(favourite) }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Text(text = favourite.city, style = MaterialTheme.typography.subtitle2)
            Text(text = favourite.country, style = MaterialTheme.typography.subtitle1)
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                tint = Color.Red.copy(alpha = 0.9f),
                modifier = Modifier.clickable {
                    favouriteViewModel.deleteFavourite(favourite = favourite)
                }
            )

        }

    }


}