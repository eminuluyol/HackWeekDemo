package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.taurus.hackweekdemo.core.utils.snaphelper.StartSnapHelper
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateMapPinPositionCommand
import com.taurus.hackweekdemo.notification.service.LocationServiceHelper
import kotlinx.android.synthetic.main.fragment_home_screen.view.*

internal class HomeScreenViewContainer(
        contentView: View,
        private val carFeedsAdapter: CarFeedsAdapter,
        commandProcessor: CommandProcessor,
        val serviceHelper: LocationServiceHelper
) : HomeScreenView {

    private val carFeedsList: RecyclerView = contentView.carFeedList

    init {
        carFeedsList.apply {
            adapter = carFeedsAdapter
            layoutManager = LinearLayoutManager(contentView.context, LinearLayoutManager.HORIZONTAL, false)
        }
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(carFeedsList)

        carFeedsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                commandProcessor.process(UpdateMapPinPositionCommand(carFeedsAdapter.firsVisibleItem(position)))
            }
        })
    }

    override fun updateViewState(viewState: HomeScreenViewState) {
        carFeedsAdapter.addCarFeeds(viewState.carItems)
        serviceHelper.locationService?.findNearbyCars(viewState.carItems)
    }

}
