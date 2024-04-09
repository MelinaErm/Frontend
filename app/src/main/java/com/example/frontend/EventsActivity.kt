package com.example.frontend

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityEventsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://outrageous-leather-jacket-foal.cyclic.app/"

class EventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding
    private lateinit var eventsAdapter: EventsAdapter // define adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCity = intent.getStringExtra("selectedCity") ?: ""
        val eventType = intent.getStringExtra("eventType") ?: ""
        setUpRecyclerView()

        //if eventType is provided, filter events by eventType, else fetch all events for the city
        if (eventType.isNotEmpty()) {
            getDataEventsByType(eventType)
        } else {
            getDataEvents(selectedCity)
        }

        supportActionBar?.hide()
    }

    private fun setUpRecyclerView() {
        eventsAdapter = EventsAdapter(this)
        binding.eventsRecycler.apply {
            layoutManager = LinearLayoutManager(this@EventsActivity)
            adapter = eventsAdapter
        }
    }

    private fun getDataEvents(city: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(EventService::class.java)

        val retrofitData = retrofitBuilder.getEventsForCity(city)
        retrofitData.enqueue(object : Callback<List<DataEventsItem>> {
            override fun onResponse(
                call: Call<List<DataEventsItem>>,
                response: Response<List<DataEventsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    eventsAdapter.setData(responseBody) // set data to adapter
                    updateEventCount(responseBody.size)
                } else {
                    Log.d("EventsActivity", "failed to retrieve events for city: $city")
                }
            }

            override fun onFailure(call: Call<List<DataEventsItem>>, t: Throwable) {
                Log.d("EventsActivity", "onFailure: " + t.message)
            }
        })
    }

    private fun getDataEventsByType(eventType: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(EventService::class.java)

        val retrofitData = retrofitBuilder.getEventsForType(eventType)
        retrofitData.enqueue(object : Callback<List<DataEventsItem>> {
            override fun onResponse(
                call: Call<List<DataEventsItem>>,
                response: Response<List<DataEventsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    eventsAdapter.setData(responseBody) // set data to adapter
                    updateEventCount(responseBody.size)
                } else {
                    Log.d("EventsActivity", "failed to retrieve events for type: $eventType")
                }
            }

            override fun onFailure(call: Call<List<DataEventsItem>>, t: Throwable) {
                Log.d("EventsActivity", "onFailure: " + t.message)
            }
        })
    }

    private fun updateEventCount(count: Int) {
        binding.eventCountTextview.text = "Search Results: $count"
    }



}
