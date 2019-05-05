package com.chrispconnolly.weatherapp

import java.text.SimpleDateFormat
import java.util.*

fun getImageSrc(icon:String):Int{
    return when(icon){
        "clear-day" -> R.drawable.fill_6
        "clear-night" -> R.drawable.group_34
        "rain" -> R.drawable.group_27
        "snow" -> R.drawable.group_3
        "sleet" -> R.drawable.group_3
        "wind" -> R.drawable.group_10
        "fog" -> R.drawable.group_10
        "cloudy" -> R.drawable.cloudy
        "partly-cloudy-day" -> R.drawable.cloudy_sunny
        "partly-cloudy-night" -> R.drawable.night_raining_cloudy
        "hail" -> R.drawable.group_3
        "thunderstorm" -> R.drawable.group_6
        "tornado" ->  R.drawable.group_6
        else -> R.drawable.cloudy_sunny
    }
}

fun formatTemperature(temperature:Double):String{
    return String.format("${temperature.toInt()}º")
}

fun formatHighLow(high:Double, low:Double):String{
    return String.format("${high.toInt()}º/${low.toInt()}º")
}

fun formatPrecipProbability(precipProbability:Double):String{
    return String.format("${(precipProbability*100).toInt()}%%")
}

fun formatTime(timezone:String):String{
    var simpleDateFormat = SimpleDateFormat("hh:mm")
    simpleDateFormat.timeZone = TimeZone.getTimeZone(timezone)
    return simpleDateFormat.format(Date())
}

fun formatDate(milliseconds:Int):String{
    return SimpleDateFormat("MM/dd/yyyy HH:mm").format(Date(milliseconds*1000L))
}