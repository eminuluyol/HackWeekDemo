package com.taurus.hackweekdemo.home.viewstate.actions

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.StateMutation
import com.taurus.hackweekdemo.home.viewstate.commands.NewItemsCommand
import com.taurus.hackweekdemo.home.viewstate.singleMutation
import io.reactivex.Observable

internal object NewItemsAction : ViewStateLoop.ObservableAction<NewItemsCommand, HomeScreenViewState> {

    override fun invoke(command: NewItemsCommand): Observable<StateMutation> = singleMutation {
        copy(carItems = command.items)
    }

}
