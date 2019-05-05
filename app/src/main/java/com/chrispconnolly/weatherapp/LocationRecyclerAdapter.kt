package com.chrispconnolly.weatherapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class LocationRecyclerAdapter(private val weatherModelArrayList: ArrayList<WeatherModel>) : RecyclerView.Adapter<LocationRecyclerAdapter.LocationViewHolder>() {

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var locationTextView: TextView = view.findViewById(R.id.location)
        var iconImageView: ImageView = view.findViewById(R.id.icon)
        var temperatureTextView: TextView = view.findViewById(R.id.temperature)
        var highLowTextView: TextView = view.findViewById(R.id.highLow)
        var precipProbabilityTextView: TextView = view.findViewById(R.id.precipProbability)
        var timeTextView: TextView = view.findViewById(R.id.time)
        var view:View = view
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_recycler_view, parent, false) as View
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val weatherModel = weatherModelArrayList[position]
        val currently = weatherModel.currently
        val daily = weatherModel.daily.data[0]
        holder.locationTextView.text = weatherModel.location
        holder.iconImageView.imageResource = getImageSrc(currently.icon)
        holder.temperatureTextView.text = formatTemperature(currently.temperature)
        holder.highLowTextView.text = formatHighLow(daily.temperatureHigh, daily.temperatureLow)
        holder.precipProbabilityTextView.text = formatPrecipProbability(currently.precipProbability)
        holder.timeTextView.text = formatTime(weatherModel.timezone)

        holder.view.onClick {
            val intent = Intent(holder.context, DetailActivity::class.java)
            intent.putExtra("location", weatherModel.location)
            intent.putExtra("latLong", String.format("${weatherModel.latitude},${weatherModel.longitude}"))
            startActivity(holder.context, intent, null)
        }
    }

    override fun getItemCount() = weatherModelArrayList.size
}