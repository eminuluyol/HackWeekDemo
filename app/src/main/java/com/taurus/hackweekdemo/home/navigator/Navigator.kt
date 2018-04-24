package com.taurus.hackweekdemo.home.navigator

import android.support.v4.app.FragmentActivity
import android.view.View
import com.taurus.hackweekdemo.home.data.CarItem

interface Navigator {

    fun bind(activity: FragmentActivity?)

    fun launchDetailActivity(sharedView : View, carItem : CarItem)

    fun unbind()
}
