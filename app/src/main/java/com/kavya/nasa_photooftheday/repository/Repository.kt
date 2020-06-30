package com.kavya.nasa_photooftheday.repository

import androidx.lifecycle.LiveData
import com.kavya.nasa_photooftheday.repository.network.ApodNetworkManager
import com.kavya.nasa_photooftheday.repository.network.ApodResponse
import com.kavya.nasa_photooftheday.viewmodel.state.Response

/**
 * Repository of APOD data
 *
 *
 * Created by Kavya P S on 29/06/20.
 */
object Repository {

    fun fetchApodForCurrentDay(): LiveData<Response<ApodResponse>> {
        return ApodNetworkManager.getLiveCurrentDayApod()
    }

    fun fetchApodForDate(date: String): LiveData<Response<ApodResponse>> {
        return ApodNetworkManager.getLiveApodForDate(date)
    }
}
