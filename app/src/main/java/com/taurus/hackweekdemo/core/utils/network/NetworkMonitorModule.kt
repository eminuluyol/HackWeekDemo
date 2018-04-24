package com.taurus.hackweekdemo.core.utils.network

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class NetworkMonitorModule {

    @Provides
    @Singleton
    internal fun providesNetworkChangeListener(context: Context): NetworkStateChangeListener = NetworkChangeReceiver.create(context)

    @Provides
    @Singleton
    internal fun provideConnectivityManager(context: Context) : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    internal fun providesConnectivityMonitor(
            connectivityManager: ConnectivityManager,
            networkStateChangeListener: NetworkStateChangeListener
    ): ConnectivityMonitor = ConnectivityMonitorImpl(connectivityManager, networkStateChangeListener)



}
