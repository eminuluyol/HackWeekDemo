package com.taurus.hackweekdemo.home

import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.home.view.HomeScreenViewContainer
import com.taurus.hackweekdemo.home.view.SnackbarViewContainer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

internal class HomeScreenPresenter constructor(
        private val schedulingStrategy: SchedulingStrategy,
        private val viewModel: HomeViewModel
) : Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun bind(
            homeScreenViewContainer: HomeScreenViewContainer,
            snackbarViewContainer: SnackbarViewContainer
    ) {
        compositeDisposable += viewModel.viewStateStream()
                .compose(schedulingStrategy.apply())
                .subscribeBy(
                        onNext = { state ->
                            homeScreenViewContainer.updateViewState(state)
                            snackbarViewContainer.updateViewState(state)
                        }
                )

    }

    override fun unbind() {
        compositeDisposable.clear()
    }

}
