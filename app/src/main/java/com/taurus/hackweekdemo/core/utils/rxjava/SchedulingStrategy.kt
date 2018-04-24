package com.taurus.hackweekdemo.core.utils.rxjava

import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class SchedulingStrategy constructor(
        private val executor: Scheduler,
        private val notifier: Scheduler
) {

    fun <T> apply(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                    .subscribeOn(executor)
                    .observeOn(notifier)
        }
    }

    fun <T> applyToFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable
                    .subscribeOn(executor)
                    .observeOn(notifier)
        }
    }

    fun applyToCompletable(): CompletableTransformer {
        return CompletableTransformer { completable ->
            completable
                    .subscribeOn(executor)
                    .observeOn(notifier)
        }
    }

    fun <T> applyToSingle(): SingleTransformer<T, T> {
        return SingleTransformer { single ->
            single
                    .subscribeOn(executor)
                    .observeOn(notifier)
        }
    }

    fun <T> applyToMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer { maybe ->
            maybe
                    .subscribeOn(executor)
                    .observeOn(notifier)
        }
    }

    fun getExecutor(): Scheduler {
        return executor
    }

    fun getNotifier(): Scheduler {
        return notifier
    }

    fun immediate(): SchedulingStrategy {
        return SchedulingStrategy(Schedulers.trampoline(), Schedulers.trampoline())
    }
}
