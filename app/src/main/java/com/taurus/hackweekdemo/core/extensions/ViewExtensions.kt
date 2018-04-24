package com.taurus.hackweekdemo.core.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

