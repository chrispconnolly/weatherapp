package com.chrispconnolly.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LocationViewModel : ViewModel() {
    private val weatherModelArrayList:ArrayList<WeatherModel> = ArrayList()
    private val weatherModels: MutableLiveData<ArrayList<WeatherModel>> by lazy {
        MutableLiveData<ArrayList<WeatherModel>>().also {
            loadWeatherModel()
        }
    }
    fun getWeatherModels(): LiveData<ArrayList<WeatherModel>> {
        return weatherModels
    }
    private fun loadWeatherModel() {
        sendDarkSkyRequest("McLean, Virginia", "38.9339,-77.1773")
        sendDarkSkyRequest("Nantes, France", "47.2184,-1.5536")
        sendDarkSkyRequest("Council Bluffs, Iowa", "41.2619,-95.8608")
    }

    private fun sendDarkSkyRequest(location:String, latLong:String){
        doAsync {
            val model = getDarkSkyData(latLong)
            uiThread {
                model.location = location
                weatherModelArrayList.add(model)
                weatherModels.postValue(weatherModelArrayList)
            }
        }
    }
}