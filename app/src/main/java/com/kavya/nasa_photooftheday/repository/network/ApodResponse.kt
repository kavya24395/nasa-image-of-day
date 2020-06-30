package com.kavya.nasa_photooftheday.repository.network

import com.google.gson.annotations.SerializedName

/**
 * POJO:
 * Response for NASA's Astronomy Picture of the Day (APOD) call
 *
 * Created by Kavya P S on 29/06/20.
 */

class ApodResponse {
    @SerializedName("copyright")
    val copyright: String? = null

    @SerializedName("date")
    val date: String? = null

    @SerializedName("explanation")
    val explanation: String? = null

    @SerializedName("hdurl")
    val hdUrl: String? = null

    @SerializedName("media_type")
    val mediaType: String? = null

    @SerializedName("service_version")
    val serviceVersion: String? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("url")
    val url: String? = null

}
