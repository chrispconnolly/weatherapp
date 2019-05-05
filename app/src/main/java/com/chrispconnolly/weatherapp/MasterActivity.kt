package com.chrispconnolly.weatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MasterActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        val locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.getWeatherModels().observe(this, Observer<ArrayList<WeatherModel>>
        { weatherModels ->
            viewManager = LinearLayoutManager(this)
            viewAdapter = LocationRecyclerAdapter(weatherModels)

            recyclerView = findViewById<RecyclerView>(R.id.locationRecyclerView).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Toast.makeText(this, "Write code to add a new location.", Toast.LENGTH_LONG).show()
        }
    }
}
