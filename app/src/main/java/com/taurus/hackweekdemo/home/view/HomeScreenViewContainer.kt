package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.RecyclerView
import android.view.View
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import kotlinx.android.synthetic.main.fragment_home_screen.view.*

internal class HomeScreenViewContainer(
        contentView: View,
        private val carFeedsAdapter: CarFeedsAdapter,
        commandProcessor: CommandProcessor
) : HomeScreenView {

    private val carFeedsList: RecyclerView = contentView.carFeedList

    init {
        carFeedsList.apply {
            adapter = carFeedsAdapter
        }
    }

    override fun updateViewState(viewState: HomeScreenViewState) {
        carFeedsAdapter.addCarFeeds(viewState.carItems)
    }

}
