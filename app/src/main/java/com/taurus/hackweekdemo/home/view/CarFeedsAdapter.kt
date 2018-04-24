package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.extensions.autoNotify
import com.taurus.hackweekdemo.core.extensions.inflate
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import kotlinx.android.synthetic.main.car_feeds_list_item.view.*

internal class CarFeedsAdapter(
        private val navigator: Navigator
) : RecyclerView.Adapter<CarFeedsViewHolder>() {

    private val carFeedList = mutableListOf<CarItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CarFeedsViewHolder(
                    parent.inflate(R.layout.car_feeds_list_item),
                    navigator
            )

    override fun getItemCount() = carFeedList.size

    override fun onBindViewHolder(holder: CarFeedsViewHolder, position: Int) {
        holder.render(carFeedList[position])
    }

    override fun onViewDetachedFromWindow(holder: CarFeedsViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.image_view_car_photo.clearAnimation()
    }

    fun addCarFeeds(updatedSavedSearches: List<CarItem>) {
        val itemsBeforeUpdate = carFeedList.toList()
        carFeedList.clear()
        carFeedList.addAll(updatedSavedSearches)

        autoNotify(itemsBeforeUpdate, updatedSavedSearches) { oldItem, newItem ->
            oldItem.address == newItem.address
        }
    }

    fun firsVisibleItem(position: Int): CarItem = carFeedList[position]

}
