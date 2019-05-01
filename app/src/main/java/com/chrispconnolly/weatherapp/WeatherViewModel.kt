package com.chrispconnolly.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.HttpURLConnection
import java.net.URL

class WeatherViewModel : ViewModel() {
    private val weatherModel: MutableLiveData<WeatherModel> by lazy {
        MutableLiveData<WeatherModel>().also {
            loadWeatherModel()
        }
    }

    fun getWeatherModel(): LiveData<WeatherModel> {
        return weatherModel
    }

    private fun loadWeatherModel() {
        doAsync {
            val connection = URL("https://api.darksky.net/forecast/[INSERT YOUR API KEY]/38.9339,77.1773")
                .openConnection() as HttpURLConnection
            val model: WeatherModel = Gson().fromJson(
                connection.inputStream.bufferedReader().use { it.readText() },
                WeatherModel::class.java)
            uiThread {
                weatherModel.postValue(model)
            }
        }
    }
}