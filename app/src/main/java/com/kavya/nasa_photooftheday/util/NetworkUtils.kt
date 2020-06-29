package com.kavya.nasa_photooftheday.util

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network Utility Manager:
 * Obtain NetworkService to make API calls using this class.
 *
 * Created by Kavya P S on 29/06/20.
 */
object NetworkUtils {

    private val mHttpClientBuilder = OkHttpClient.Builder()
    private val mRetrofit: Retrofit
    private const val mApodBaseUrl = "https://api.nasa.gov/planetary/"

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mHttpClientBuilder.addInterceptor(loggingInterceptor)

        mRetrofit = Retrofit.Builder()
            .baseUrl(mApodBaseUrl)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(mHttpClientBuilder.build())
            .build()
    }

    fun <T> getNetworkHook(ApiClass: Class<T>): T {
        return mRetrofit.create(ApiClass)
    }
}