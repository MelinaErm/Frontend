package com.example.frontend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {

    @GET("events/{city}")
    fun getEventsForCity(@Path("city") city: String): Call<List<DataEventsItem>>
    //@GET(value = "events")
    //fun getEvents(): Call<List<DataEventsItem>>
}
