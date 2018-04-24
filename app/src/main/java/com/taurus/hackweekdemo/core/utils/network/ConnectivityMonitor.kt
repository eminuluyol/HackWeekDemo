package com.taurus.hackweekdemo.core.utils.network

import io.reactivex.Observable

interface ConnectivityMonitor {

    val networkConnectedUpdate: Observable<Boolean>

}
