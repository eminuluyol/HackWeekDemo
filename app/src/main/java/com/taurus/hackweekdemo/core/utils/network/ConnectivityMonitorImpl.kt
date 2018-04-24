package com.taurus.hackweekdemo.core.utils.network

import android.net.ConnectivityManager
import io.reactivex.Observable
import javax.inject.Inject

internal class  ConnectivityMonitorImpl @Inject constructor(
        private val connectivityManager: ConnectivityManager,
        networkStateChangeListener: NetworkStateChangeListener
) : ConnectivityMonitor {

    override val networkConnectedUpdate: Observable<Boolean> = networkStateChangeListener.stateChange
            .map { hasConnection() }
            .distinctUntilChanged()

    private fun hasConnection(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}
