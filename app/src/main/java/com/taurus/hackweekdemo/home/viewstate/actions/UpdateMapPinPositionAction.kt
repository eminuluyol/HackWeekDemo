package com.taurus.hackweekdemo.home.viewstate.actions

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.StateMutation
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateMapPinPositionCommand
import com.taurus.hackweekdemo.home.viewstate.singleMutation
import io.reactivex.Observable

internal object UpdateMapPinPositionAction : ViewStateLoop.ObservableAction<UpdateMapPinPositionCommand, HomeScreenViewState> {

    override fun invoke(command: UpdateMapPinPositionCommand): Observable<StateMutation>  = singleMutation {
        copy(selectedCarItem = command.carItem)
    }
}
