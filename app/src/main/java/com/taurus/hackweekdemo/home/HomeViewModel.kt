package com.taurus.hackweekdemo.home

import android.arch.lifecycle.ViewModel
import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.core.utils.network.ConnectivityMonitor
import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.CommandRouter
import com.taurus.hackweekdemo.home.viewstate.GenericError
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.commands.HomeScreenCommand
import com.taurus.hackweekdemo.home.viewstate.commands.NewItemsCommand
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateNetworkConnectivityCommand
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateSnackbarCommand
import com.taurus.hackweekdemo.network.CarFeedsService
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

internal class HomeViewModel @Inject constructor(
        stateLoop: ViewStateLoop<HomeScreenCommand, HomeScreenViewState>,
        commandRouter: CommandRouter,
        commandProcessor: CommandProcessor,
        connectivityMonitor: ConnectivityMonitor,
        private val carFeedsService: CarFeedsService,
        private val schedulingStrategy: SchedulingStrategy
) : ViewModel() {

    private val initialViewState = HomeScreenViewState()

    fun viewStateStream(): Observable<HomeScreenViewState> = homeScreenStream

    private val homeScreenStream = BehaviorSubject.createDefault(initialViewState).toSerialized()

    init {
        stateLoop.configure(homeScreenStream, commandRouter)
        subscribeToHomeScreenRepository(commandProcessor)
        subscribeToNetworkChanges(connectivityMonitor, commandProcessor)
    }

    private fun subscribeToHomeScreenRepository(commandProcessor: CommandProcessor) {
        carFeedsService.allCarFeeds()
                .compose(schedulingStrategy.applyToSingle()).subscribeBy(
                        onSuccess = { carItems ->
                            commandProcessor.process(NewItemsCommand(carItems))
                        },
                        onError = {
                            commandProcessor.process(UpdateSnackbarCommand(newState = GenericError))
                        }
                )
    }

    private fun subscribeToNetworkChanges(connectivityMonitor: ConnectivityMonitor, commandProcessor: CommandProcessor) {
        connectivityMonitor.networkConnectedUpdate
                .subscribe { isConnected ->
                    commandProcessor.process(UpdateNetworkConnectivityCommand(isConnected))
                }
    }
}
