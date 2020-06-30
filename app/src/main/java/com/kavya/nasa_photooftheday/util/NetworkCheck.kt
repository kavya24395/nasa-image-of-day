package com.kavya.nasa_photooftheday.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.ref.WeakReference

/**
 * Created by Kavya P S on 29/06/20.
 */

object NetworkCheck {
    fun isConnected(contextWeakRef: WeakReference<Context>): Boolean {
        val context = contextWeakRef.get()
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true;
    }
}
