package com.kavya.nasa_photooftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.kavya.nasa_photooftheday.repository.Repository
import com.kavya.nasa_photooftheday.repository.network.ApodResponse
import com.kavya.nasa_photooftheday.viewmodel.data.ApodData
import com.kavya.nasa_photooftheday.viewmodel.state.Response

/**
 * Created by Kavya P S on 29/06/20.
 */
class ApodViewModel : ViewModel() {

    //    private lateinit var apodLiveData: MutableLiveData<Response<ApodResponse>>
    private lateinit var apodLiveData: MutableLiveData<Response<ApodData>>

    //    fun fetchPicOfDay(date: String = ""): LiveData<Response<ApodResponse>> {
    fun fetchPicOfDay(date: String = ""): LiveData<Response<ApodData>> {

        val repoLiveData = when (date) {
            "" -> Repository.fetchApodForCurrentDay()
            else -> Repository.fetchApodForDate(date)
        }
        return Transformations.switchMap(repoLiveData) {
            getfilteredLiveData(it)
        }
    }

    private fun getfilteredLiveData(repoResponse: Response<ApodResponse>): LiveData<Response<ApodData>> {

        val repoData = repoResponse.data

        val filteredData = repoData?.let {
            ApodData(
                repoData.date ?: "",
                repoData.explanation ?: "",
                repoData.hdUrl ?: "",
                repoData.mediaType ?: "",
                repoData.title ?: "",
                repoData.url ?: ""
            )
        }
//        return Response(repoResponse.state, filteredData, repoResponse.error)
        val resp = Response(repoResponse.state, filteredData, repoResponse.error)
        apodLiveData = MutableLiveData()
        apodLiveData.value = resp
        return apodLiveData
    }

}