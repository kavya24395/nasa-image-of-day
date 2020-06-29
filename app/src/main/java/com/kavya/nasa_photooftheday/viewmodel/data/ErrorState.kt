package com.kavya.nasa_photooftheday.viewmodel.data

/**
 * Created by Kavya P S on 29/06/20.
 */

enum class ErrorType(val value: Int) {
    NETWORK_ERROR(0),
    GENERAL_ERROR(1),
    SERVER_ERROR(2)
}

data class Error(
    var errorType: ErrorType,
    var message: String
)