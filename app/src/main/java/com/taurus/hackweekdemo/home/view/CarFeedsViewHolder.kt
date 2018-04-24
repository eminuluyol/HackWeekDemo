package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor

internal class CarFeedsViewHolder(
        itemView: View,
        private val commandProcessor: CommandProcessor,
        private val navigator: Navigator
) : RecyclerView.ViewHolder(itemView) {

    private val itemLayout: View = itemView.rootView

    fun render(carItem: CarItem) {

        itemLayout.setOnClickListener{
            navigator.launchDetailActivity()
        }
    }
}
