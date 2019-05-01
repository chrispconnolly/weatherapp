package com.chrispconnolly.weatherapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.imageResource


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
        findViewById<ImageView>(R.id.weatherIcon).imageResource = getImageSrc(weatherModel.currently.icon)
        loadTextView(R.id.temperature, String.format("${weatherModel.currently.temperature.toInt()}º"))
        loadTextView(R.id.summary, weatherModel.currently.summary)
        loadTextView(R.id.highLow, String.format("${weatherModel.daily.data[0].temperatureHigh.toInt()}º/" +
                    "${weatherModel.daily.data[0].temperatureLow.toInt()}º"))
        loadTextView(R.id.precipProbability, String.format("${(weatherModel.currently.precipProbability*100).toInt()}%%"))
        loadTextView(R.id.windSpeedValue, String.format("${weatherModel.currently.windSpeed} MPH"))
        loadTextView(R.id.humidityValue, String.format("${(weatherModel.currently.humidity*100).toInt()}%%"))
        loadTextView(R.id.uvindexValue, String.format(getUvIndexString(weatherModel.currently.uvIndex)))
        loadTextView(R.id.alert, getAlertsString(weatherModel))
    }

    private fun loadTextView(id:Int, text:String){
        findViewById<TextView>(id).text = text
    }

    private fun getImageSrc(icon:String):Int{
        return when(icon){
            "clear-day" -> R.drawable.cloudy_sunny
            "clear-night" -> R.drawable.group_34
            "rain" -> R.drawable.group_27
            "snow" -> R.drawable.cloudy_sunny //fix
            "sleet" -> R.drawable.group_27 //fix
            "wind" -> R.drawable.cloudy_sunny //fix
            "fog" -> R.drawable.cloudy_sunny //fix
            "cloudy" -> R.drawable.cloudy
            "partly-cloudy-day" -> R.drawable.cloudy_sunny
            "partly-cloudy-night" -> R.drawable.cloudy //fix
            "hail" -> R.drawable.cloudy_sunny //fix
            "thunderstorm" -> R.drawable.group_27 //fix
            "tornado" ->  R.drawable.group_27 //fix
            else -> R.drawable.cloudy_sunny
        }
    }

    private fun getUvIndexString(uvindex:Int):String{
        var result = when(uvindex) {
            in 0..2 -> "Low"
            in 3..5 -> "Moderate"
            in 6..7 -> "High"
            in 8..10 -> "Very High"
            in 11..20 -> "Extreme"
            else -> "Low"
        }
        return String.format("$result $uvindex")
    }

    private fun getAlertsString(weatherModel:WeatherModel):String{
        var result = ""
        if(weatherModel.alerts != null) {
            for (alert: WeatherModel.Alert in weatherModel.alerts) {
                result += String.format("${alert.title} - Expires: ${alert.expires} - ${alert.description}\n")
            }
        }
        return result
    }
}