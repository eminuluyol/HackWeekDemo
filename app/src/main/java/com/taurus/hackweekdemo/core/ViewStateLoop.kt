package com.taurus.hackweekdemo.core

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.Subject

internal class ViewStateLoop<Command, State>(
        private val commandSource: Observable<Command>,
        private val backgroundScheduler: Scheduler = Schedulers.io()
) {

    fun configure(
            stateSubject: Subject<State>,
            action: ObservableAction<Command, State>
    ) =
            commandSource
                    .flatMap { command ->
                        subscribeToMutations(command, stateSubject, action)
                    }
                    .subscribe(stateSubject)

    private fun subscribeToMutations(
            command: Command,
            stateStream: Observable<State>,
            action: ObservableAction<Command, State>
    ) =
            action.invoke(command)
                    .withLatestFrom(stateStream)
                    .map { (mutation, state) ->
                        mutation(state)
                    }
                    .subscribeOn(backgroundScheduler)

    interface ObservableAction<Command, State> {
        operator fun invoke(command: Command): Observable<State.() -> State>
    }
}
