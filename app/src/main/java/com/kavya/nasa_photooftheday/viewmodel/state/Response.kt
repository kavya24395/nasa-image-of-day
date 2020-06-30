package com.kavya.nasa_photooftheday.viewmodel.state

/**
 * Created by Kavya P S on 29/06/20.
 */
data class Response<T>(
    var state: Status,
    var data: T? = null,
    var error: Error? = null
)