package com.codezync.weatherapp.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.codezync.weatherapp.model.Weather
import com.codezync.weatherapp.model.WeatherItem
import com.codezync.weatherapp.utills.roundToDecimalPlaces
import com.codezync.weatherapp.utills.toCustomDateString
import com.codezync.weatherapp.utills.toCustomDateTimeString

@Composable
fun circleContent(data: Weather) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[1].weather[0].icon}.png"
    val weatherItem = data.list[0]
    androidx.compose.material.Surface(
        modifier = Modifier
            .size(200.dp)
            .padding(4.dp),
        shape = CircleShape,
        color = Color(0xFFFFC400),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            WeatheStateImage(imageUrl = imageUrl)

            Text(
                text = weatherItem.temp.day.roundToDecimalPlaces(0).toInt().toString() + "°",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )

            Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
        }


    }

}

@Composable
fun WeatheStateImage(imageUrl: String) {

    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "icon",
        modifier = Modifier.size(80.dp)
    )


}

@Composable
fun HumidyWindPresserRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }

        Row {
            Text(text = "${weather.pressure}% psi", style = MaterialTheme.typography.caption)
        }

        Row {
            Text(text = "${weather.speed} "+ if(isImperial) "mph" else "m/s", style = MaterialTheme.typography.caption)
        }


    }


}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = weather.sunrise.toLong().toCustomDateTimeString(),
                style = MaterialTheme.typography.caption
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = weather.sunset.toLong().toCustomDateTimeString(),
                style = MaterialTheme.typography.caption
            )
        }


    }


}


@Composable
fun WeatherListItem(item: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
    androidx.compose.material.Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(50),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.dt.toLong().toCustomDateString().substringBefore(",").trim(),
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold
            )
            WeatheStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(2.dp),
                shape = RoundedCornerShape(50),
                color = Color(0xFFFFC400)
            ) {
                Text(text = item.weather[0].main)

            }

            Text(text = buildAnnotatedString {

                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(item.temp.min.toString() + "°")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(item.temp.max.toString() + "°")
                }

            })

        }
    }
}