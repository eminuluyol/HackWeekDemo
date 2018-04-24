package com.taurus.hackweekdemo.home.viewstate

import com.taurus.hackweekdemo.home.viewstate.commands.HomeScreenCommand
import io.reactivex.subjects.PublishSubject

internal class CommandWrapper : CommandProcessor {
    val commandStream: PublishSubject<HomeScreenCommand> = PublishSubject.create<HomeScreenCommand>()

    override fun process(command: HomeScreenCommand) {
        commandStream.onNext(command)
    }
}
