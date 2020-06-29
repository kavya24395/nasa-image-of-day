package com.kavya.nasa_photooftheday.repository

import com.kavya.nasa_photooftheday.repository.network.ApodApi
import com.kavya.nasa_photooftheday.repository.network.ApodResponse
import com.kavya.nasa_photooftheday.util.NetworkUtils
import retrofit2.Call

/**
 * Repository of APOD data
 *
 *
 * Created by Kavya P S on 29/06/20.
 */
object Repository {

    fun fetchApodForCurrentDay(): Call<ApodResponse> {
        return NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForCurrentDay()
    }

    fun fetchApodForDate(date: String): Call<ApodResponse> {
        return NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForDate(date)
    }
}
