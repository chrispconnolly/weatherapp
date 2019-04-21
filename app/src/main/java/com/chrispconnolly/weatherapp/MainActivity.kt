package com.chrispconnolly.weatherapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        weatherViewModel.getWeatherModel().observe(this, Observer<WeatherModel> { weatherModel ->
            loadData(weatherModel)
        })
    }

    private fun loadData(weatherModel:WeatherModel){
        loadTextView(R.id.current_time, formatDate(weatherModel.currently.time.toLong()))
        loadTextView(R.id.current_temperature, weatherModel.currently.temperature.toString() + " degrees")
        loadTextView(R.id.current_summary, weatherModel.currently.summary)
    }

    private fun loadTextView(id:Int, text:String){
        findViewById<TextView>(id).text = text
    }

    private fun formatDate(timeInMillis:Long):String{
        var calendar = Calendar.getInstance()
        calendar.setTimeInMillis(timeInMillis)
        return calendar.time.toString()
    }
}