package com.taurus.hackweekdemo.home.navigator

import android.support.v4.app.FragmentActivity

internal class HomeScreenNavigator : Navigator {

    private var activity: FragmentActivity? = null

    override fun bind(activity: FragmentActivity?) {
        this.activity = activity
    }

    override fun launchDetailActivity() {
        //TODO
    }

    override fun unbind() {
        activity = null
    }
}
