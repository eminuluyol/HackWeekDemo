package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.extensions.autoNotify
import com.taurus.hackweekdemo.core.extensions.inflate
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor

internal class CarFeedsAdapter(
        private val commandProcessor: CommandProcessor,
        private val navigator: Navigator
) : RecyclerView.Adapter<CarFeedsViewHolder>() {

    private val carFeedList = mutableListOf<CarItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CarFeedsViewHolder(
                parent.inflate(R.layout.car_feeds_list_item),
                commandProcessor,
                navigator
        )

    override fun getItemCount() = carFeedList.size

    override fun onBindViewHolder(holder: CarFeedsViewHolder, position: Int) {
        holder.render(carFeedList[position])
    }

    fun addCarFeeds(updatedSavedSearches: List<CarItem>) {
        val itemsBeforeUpdate = carFeedList.toList()
        carFeedList.clear()
        carFeedList.addAll(updatedSavedSearches)

        autoNotify(itemsBeforeUpdate, updatedSavedSearches) { oldItem, newItem ->
            oldItem.address == newItem.address
        }
    }

}
