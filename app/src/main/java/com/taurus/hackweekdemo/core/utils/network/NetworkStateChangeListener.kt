package com.taurus.hackweekdemo.core.utils.network

import io.reactivex.Observable

internal interface NetworkStateChangeListener {

    object NetworkStateChange

    val stateChange: Observable<NetworkStateChange>

}
