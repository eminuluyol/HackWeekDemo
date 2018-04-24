package com.taurus.hackweekdemo.home.viewstate

import io.reactivex.Observable

internal typealias StateMutation = HomeScreenViewState.() -> HomeScreenViewState
internal fun singleMutation(mutation: StateMutation) = Observable.just(mutation)
