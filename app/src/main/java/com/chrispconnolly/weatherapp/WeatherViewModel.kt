package com.chrispconnolly.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class WeatherViewModel : ViewModel() {
    var location:String = "McLean, Virginia"
    var latLong:String = "38.9339,77.1773"

    private val weatherModel: MutableLiveData<WeatherModel> by lazy {
        MutableLiveData<WeatherModel>().also {
            loadWeatherModel()
        }
    }

    fun getWeatherModel(location:String, latLong:String): LiveData<WeatherModel> {
        this.location = location
        this.latLong = latLong
        return weatherModel
    }

    private fun loadWeatherModel() {
        doAsync {
            val model = getDarkSkyData(latLong)
            uiThread {
                model.location = location
                weatherModel.postValue(model)
            }
        }
    }
}