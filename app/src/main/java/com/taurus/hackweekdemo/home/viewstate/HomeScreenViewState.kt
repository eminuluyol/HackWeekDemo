package com.taurus.hackweekdemo.home.viewstate

import com.taurus.hackweekdemo.home.data.CarItem

internal data class HomeScreenViewState(
        val carItems: List<CarItem> = emptyList(),
        val snackbarState: SnackbarState = Hidden,
        val isConnectedToTheInternet: Boolean = true,
        val mapState: MapState = Idle,
        val selectedCarItem : CarItem? = null
)
