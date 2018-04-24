package com.taurus.hackweekdemo.home.viewstate

import com.taurus.hackweekdemo.home.viewstate.commands.HomeScreenCommand

internal interface CommandProcessor {
    fun process(command: HomeScreenCommand)
}
