package com.victor.dogbreeds.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppUtil {
    companion object {
        fun <T> getNetworkThread(): ObservableTransformer<T, T>? {
            return ObservableTransformer { observable: Observable<T> ->
                observable.subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}

fun getConnectionType(context: Context): ConnectionType {
    var result = ConnectionType.None
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = ConnectionType.WiFi
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = ConnectionType.Mobile
                } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
                    result = ConnectionType.VPN
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        result = ConnectionType.WiFi
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        result = ConnectionType.Mobile
                    }
                    ConnectivityManager.TYPE_VPN -> {
                        result = ConnectionType.VPN
                    }
                }
            }
        }
    }
    return result
}