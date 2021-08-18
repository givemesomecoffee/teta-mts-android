package ru.givemesomecoffee.tetamtsandroid.utils

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ru.givemesomecoffee.tetamtsandroid.App
import java.lang.Exception

object NetworkAccess {
var isNetworkConnected = false

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback() {

        try {
            val connectivityManager =
                App.appContext.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder();

          //  connectivityManager.registerDefaultNetworkCallback
            var networkCallback =    object: ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    Log.d("network", "onLost")
                    isNetworkConnected = false
                   super.onLost(network)
                    //record wi-fi disconnect event
                }
                override fun onUnavailable() {
                    Log.d("network", "onUnavailable")
                    isNetworkConnected = false
                    super.onUnavailable()
                }
                override fun onLosing(network: Network, maxMsToLive: Int) {
                    Log.d("network", "onLosing")
                   super.onLosing(network, maxMsToLive)
                }
                override fun onAvailable(network: Network) {
                    isNetworkConnected = true
                    Log.d("network", "onAvailable")
                    super.onAvailable(network)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } catch (e: Exception) {
            isNetworkConnected = false
        }

    }
}