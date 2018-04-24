package com.taurus.hackweekdemo.home.viewstate.commands

import android.content.Context
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.data.SelectedCarItem
import com.taurus.hackweekdemo.home.viewstate.SnackbarState

internal sealed class HomeScreenCommand

internal data class UpdateNetworkConnectivityCommand(val isConnected : Boolean) : HomeScreenCommand()

internal data class UpdateSnackbarCommand(val newState: SnackbarState) : HomeScreenCommand()

internal data class NewItemsCommand(val items: List<CarItem>) : HomeScreenCommand()

internal data class CheckGooglePlayServiceAvailabilityCommand(val context: Context) : HomeScreenCommand()

internal data class UpdateMapPinPositionCommand(val carItem: SelectedCarItem) : HomeScreenCommand()


