package com.codezync.weatherapp.widgets

import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.codezync.weatherapp.model.Favourite
import com.codezync.weatherapp.navigation.WeatherScreens
import com.codezync.weatherapp.utills.Constants.items
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun WeatherAppBar(
    title: String = "Title",
    isInfavouriteList : List<Favourite> = arrayListOf(),
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elavation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onFavouriteClicked: (Boolean) -> Unit = {},
    onButtonClicked: () -> Unit = {}


) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    val isFavourite = remember {
        mutableStateOf(false)
    }


    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    TopAppBar(title = {
        Text(
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = { onAddActionClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")

            }

            IconButton(onClick = {
                showDialog.value = true
                onButtonClicked.invoke()
            }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icon")

            }


        } else {
            Box {}
        }
    }, navigationIcon = {
        if (icon != null) {
            Icon(imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary,
                modifier = Modifier.clickable {

                    onButtonClicked.invoke()
                })
        }

        if (isMainScreen && isInfavouriteList.isNullOrEmpty()) {

            IconButton(onClick = {
                isFavourite.value = !isFavourite.value
                if (isFavourite.value == true) {
                    onFavouriteClicked.invoke(isFavourite.value)
                }

            }) {
                if (!isFavourite.value) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "More Icon"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "More Icon",
                        tint = Color.Red.copy(alpha = 0.9f)
                    )
                }

            }


        }

    }, backgroundColor = Color.Transparent, elevation = elavation
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded = remember {

        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {

        DropdownMenu(
            expanded = expanded.value, onDismissRequest = {
                expanded.value = false
                showDialog.value = false
            }, modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->

                DropdownMenuItem(onClick = {
                    expanded.value = false
                    showDialog.value = false
                }) {

                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favourites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        }, contentDescription = null, tint = Color.LightGray
                    )

                    Text(
                        text = text, modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favourites" -> WeatherScreens.FavouriteScreen.name
                                    else -> WeatherScreens.SettingScreen.name

                                }

                            )

                        }, fontWeight = FontWeight.W300
                    )

                }

            }

        }


    }


}


