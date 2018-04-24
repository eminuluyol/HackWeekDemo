package com.taurus.hackweekdemo.home.navigator

import android.support.v4.app.FragmentActivity

interface Navigator {

    fun bind(activity: FragmentActivity?)

    fun launchDetailActivity()

    fun unbind()
}
