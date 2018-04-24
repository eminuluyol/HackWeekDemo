package com.taurus.hackweekdemo.home

import com.taurus.hackweekdemo.home.view.HomeScreenViewContainer
import com.taurus.hackweekdemo.home.view.MapViewContainer
import com.taurus.hackweekdemo.home.view.SnackbarViewContainer

internal interface Presenter {
    fun bind(
            homeScreenViewContainer: HomeScreenViewContainer,
            snackbarViewContainer: SnackbarViewContainer,
            mapViewContainer: MapViewContainer
    )
    fun unbind()
}
