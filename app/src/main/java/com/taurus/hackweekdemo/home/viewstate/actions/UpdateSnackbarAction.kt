package com.taurus.hackweekdemo.home.viewstate.actions

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateSnackbarCommand
import com.taurus.hackweekdemo.home.viewstate.singleMutation

internal object UpdateSnackbarAction : ViewStateLoop.ObservableAction<UpdateSnackbarCommand, HomeScreenViewState> {
    override fun invoke(command: UpdateSnackbarCommand) = singleMutation {
        copy(snackbarState = command.newState)
    }
}
