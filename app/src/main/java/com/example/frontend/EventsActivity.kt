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
    private lateinit var eventsAdapter: EventsAdapter //define adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCity = intent.getStringExtra("selectedCity") ?: ""
        setUpRecyclerView()
        getDataEvents(selectedCity)

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
                    eventsAdapter.setData(responseBody) //set data to adapter
                } else {
                    Log.d("EventsActivity", "failed to retrieve events for city: $city")
                }
            }

            override fun onFailure(call: Call<List<DataEventsItem>>, t: Throwable) {
                Log.d("EventsActivity", "onFailure: " + t.message)
            }
        })
    }
}
