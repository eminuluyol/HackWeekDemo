package com.taurus.hackweekdemo.home.viewstate.actions

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.Hidden
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.NoInternetConnection
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateNetworkConnectivityCommand
import com.taurus.hackweekdemo.home.viewstate.singleMutation

internal object UpdateNetworkConnectivityAction : ViewStateLoop.ObservableAction<UpdateNetworkConnectivityCommand, HomeScreenViewState> {
    override fun invoke(command: UpdateNetworkConnectivityCommand) =
            if (command.isConnected)
                singleMutation {
                    copy(snackbarState = Hidden, isConnectedToTheInternet = true)
                }
            else
                singleMutation {
                    copy(snackbarState = NoInternetConnection, isConnectedToTheInternet = false)
                }
}

