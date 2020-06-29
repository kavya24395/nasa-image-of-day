package com.kavya.nasa_photooftheday.viewmodel.data

/**
 * Created by Kavya P S on 29/06/20.
 */
data class Response<T>(
    var data: T,
    var state: Status,
    var error: Error? = null
)