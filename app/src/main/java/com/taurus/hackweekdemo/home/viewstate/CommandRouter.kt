package com.taurus.hackweekdemo.home.viewstate

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.commands.*
import io.reactivex.Observable

internal class CommandRouter(
        private val onUpdateNetworkConnectivityState: ViewStateLoop.ObservableAction<UpdateNetworkConnectivityCommand, HomeScreenViewState>,
        private val onSnackbarChanged: ViewStateLoop.ObservableAction<UpdateSnackbarCommand, HomeScreenViewState>,
        private val onNewItems: ViewStateLoop.ObservableAction<NewItemsCommand, HomeScreenViewState>,
        private val onCheckGooglePlayServiceAvailability: ViewStateLoop.ObservableAction<CheckGooglePlayServiceAvailabilityCommand, HomeScreenViewState>,
        private val onUpdateMapPinPosition : ViewStateLoop.ObservableAction<UpdateMapPinPositionCommand, HomeScreenViewState>
) : ViewStateLoop.ObservableAction<HomeScreenCommand, HomeScreenViewState> {

    override fun invoke(command: HomeScreenCommand): Observable<HomeScreenViewState.() -> HomeScreenViewState> {
        return when (command) {
            is UpdateNetworkConnectivityCommand -> onUpdateNetworkConnectivityState(command)
            is UpdateSnackbarCommand -> onSnackbarChanged(command)
            is NewItemsCommand -> onNewItems(command)
            is CheckGooglePlayServiceAvailabilityCommand -> onCheckGooglePlayServiceAvailability(command)
            is UpdateMapPinPositionCommand -> onUpdateMapPinPosition(command)
        }
    }
}
