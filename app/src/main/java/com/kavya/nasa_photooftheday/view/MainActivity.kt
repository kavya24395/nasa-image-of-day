package com.kavya.nasa_photooftheday.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.kavya.nasa_photooftheday.R
import com.kavya.nasa_photooftheday.databinding.ActivityMainBinding
import com.kavya.nasa_photooftheday.repository.network.ApodApi
import com.kavya.nasa_photooftheday.repository.network.ApodResponse
import com.kavya.nasa_photooftheday.util.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        /*val apod: Call<ApodResponse> = NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForCurrentDay()

        apod.enqueue(object : Callback<ApodResponse> {
            override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<ApodResponse>, response: Response<ApodResponse>) {
                println("response is SUCCESS")
            }

        })

        NetworkUtils.getNetworkHook(ApodApi::class.java)
            .fetchApodForDate("2020-05-26")
            .enqueue(object : Callback<ApodResponse> {
                override fun onFailure(call: Call<ApodResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ApodResponse>,
                    response: Response<ApodResponse>
                ) {
                    println("response is SUCCESS")

                }

            })*/
    }
}
