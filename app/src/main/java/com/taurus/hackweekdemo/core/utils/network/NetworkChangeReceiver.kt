package com.taurus.hackweekdemo.core.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

internal class NetworkChangeReceiver private constructor() : BroadcastReceiver(), NetworkStateChangeListener {

    private val eventChanges = PublishSubject.create<NetworkStateChangeListener.NetworkStateChange>()

    override fun onReceive(context: Context?, intent: Intent?) {
        eventChanges.onNext(NetworkStateChangeListener.NetworkStateChange)
    }

    override val stateChange: Observable<NetworkStateChangeListener.NetworkStateChange>
        get() = eventChanges

    companion object {
        private val INTENT_FILTER = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        fun create(context: Context): NetworkChangeReceiver {
            val networkChangeReciever = NetworkChangeReceiver()
            context.registerReceiver(networkChangeReciever, INTENT_FILTER)
            return networkChangeReciever
        }
    }

}
