package com.kavya.nasa_photooftheday.repository.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kavya.nasa_photooftheday.util.NetworkUtils
import com.kavya.nasa_photooftheday.viewmodel.state.Error
import com.kavya.nasa_photooftheday.viewmodel.state.ErrorType
import com.kavya.nasa_photooftheday.viewmodel.state.Status
import com.kavya.nasa_photooftheday.viewmodel.state.Response as dataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Kavya P S on 29/06/20.
 */
object ApodNetworkManager {
    var mutableLiveData = MutableLiveData<dataResponse<ApodResponse>>()

    fun getLiveCurrentDayApod(): LiveData<dataResponse<ApodResponse>> {

        lateinit var responseObject: dataResponse<ApodResponse>

        NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForCurrentDay()
            .enqueue(object : Callback<ApodResponse> {

                override fun onResponse(
                    call: Call<ApodResponse>,
                    response: Response<ApodResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseObject = getSuccessObject(it)
                            mutableLiveData.value = responseObject
                            return
                        }
                        responseObject =
                            getErrorObject(ErrorType.GENERAL_ERROR, "Data is null")
                    } else {
                        responseObject = getErrorObject(
                            ErrorType.SERVER_ERROR,
                            "Response.isSuccessful() returned false"
                        )
                    }
                    mutableLiveData.value = responseObject
                }

                override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                    responseObject =
                        getErrorObject(ErrorType.SERVER_ERROR, "onFailure reason: ${t.message}")
                    mutableLiveData.value = responseObject
                }
            })

        return mutableLiveData
    }

    fun getLiveApodForDate(date: String): LiveData<dataResponse<ApodResponse>> {
        lateinit var responseObject: dataResponse<ApodResponse>
        NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForDate(date = date)
            .enqueue(object : Callback<ApodResponse> {
                override fun onResponse(
                    call: Call<ApodResponse>,
                    response: Response<ApodResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            responseObject = getSuccessObject(it)
                            mutableLiveData.value = responseObject
                            return
                        }
                        responseObject =
                            getErrorObject(ErrorType.GENERAL_ERROR, "Data is null")

                    } else {
                        responseObject = getErrorObject(
                            ErrorType.SERVER_ERROR,
                            "Response.isSuccessful() returned false"
                        )
                    }

                    mutableLiveData.value = responseObject
                }


                override fun onFailure(call: Call<ApodResponse>, t: Throwable) {

                    responseObject =
                        getErrorObject(ErrorType.SERVER_ERROR, "onFailure reason: ${t.message}")

                    mutableLiveData.value = responseObject
                }
            })

        return mutableLiveData
    }

    private fun getErrorObject(type: ErrorType, message: String): dataResponse<ApodResponse> {
        return dataResponse(
            Status.ERROR,
            error = Error(type, message)
        )
    }


    private fun getSuccessObject(data: ApodResponse): dataResponse<ApodResponse> {
        return dataResponse(
            Status.SUCCESS,
            data = data
        )
    }
}