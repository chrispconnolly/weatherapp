package com.chrispconnolly.weatherapp

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

const val apiKey = "[INSERT YOUR API KEY]"

fun ViewModel.getDarkSkyData(latLong:String):WeatherModel{
    val connection = URL(String.format("https://api.darksky.net/forecast/${apiKey}/${latLong}"))
        .openConnection() as HttpURLConnection
    val weatherModel: WeatherModel = Gson().fromJson(
        connection.inputStream.bufferedReader().use { it.readText() },
        WeatherModel::class.java)
    return weatherModel
}