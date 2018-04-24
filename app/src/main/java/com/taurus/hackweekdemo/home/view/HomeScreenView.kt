package com.taurus.hackweekdemo.home.view

import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState

internal interface HomeScreenView {
    fun updateViewState(viewState: HomeScreenViewState)
}
