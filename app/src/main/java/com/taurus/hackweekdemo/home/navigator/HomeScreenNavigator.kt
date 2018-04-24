package com.taurus.hackweekdemo.home.navigator

import android.app.Activity
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.view.View
import com.taurus.hackweekdemo.detail.DetailScreenActivity
import com.taurus.hackweekdemo.home.data.CarItem

internal class HomeScreenNavigator : Navigator {

    private var activity: FragmentActivity? = null

    override fun bind(activity: FragmentActivity?) {
        this.activity = activity
    }

    override fun launchDetailActivity(sharedView: View, carItem: CarItem) {

        activity?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity as Activity,
                    sharedView,
                    "sharedImage"
            )

            activity!!.startActivity(
                    DetailScreenActivity.newInstance(
                            activity!!,
                            carItem.photoUrl),
                            options.toBundle()
            )
        }

    }

    override fun unbind() {
        activity = null
    }
}
