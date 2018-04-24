package com.taurus.hackweekdemo.core.extensions

import com.taurus.hackweekdemo.BuildConfig

inline fun debug(body: () -> Unit) {
    if (BuildConfig.DEBUG) body()
}
