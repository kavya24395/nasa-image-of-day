package com.kavya.nasa_photooftheday.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface: Retrofit calls to NASA's Astronomy Picture of the Day (APOD)
 *
 *
 * Created by Kavya P S on 29/06/20.
 */
interface ApodApi {

    @GET("apod/")
    fun fetchApodForCurrentDay(@Query("api_key") apiKey: String = "DEMO_KEY"): Call<ApodResponse>


    @GET("apod/")
    fun fetchApodForDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = "DEMO_KEY"
    ): Call<ApodResponse>

}