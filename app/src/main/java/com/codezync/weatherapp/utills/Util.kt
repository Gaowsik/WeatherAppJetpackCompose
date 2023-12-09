package com.codezync.weatherapp.utills

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.pow
import kotlin.math.round


fun Long.toCustomDateString(): String {
        val dateFormat = SimpleDateFormat("E, MMM dd", Locale.getDefault())
        return dateFormat.format(Date(this))
    }

fun Long.toCustomDateTimeString(): String {
    val dateFormat = SimpleDateFormat("hh:mm:aa", Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun Double.roundToDecimalPlaces(decimalPlaces: Int): Double {
    require(decimalPlaces >= 0) { "Decimal places should be a non-negative integer." }
    val multiplier = 10.0.pow(decimalPlaces.toDouble())
    return round(this * multiplier) / multiplier
}

enum class itemsDropDownMenu() {
    Favourites {
        override fun signal() = "Favourites"

    },
    About {
        override fun signal() = "About"

    },
    Setting {
        override fun signal() = "Setting"

    };

    abstract fun signal(): String

}

