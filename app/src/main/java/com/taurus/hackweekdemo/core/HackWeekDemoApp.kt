package com.taurus.hackweekdemo.core

import com.taurus.hackweekdemo.core.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class HackWeekDemoApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out HackWeekDemoApp> =
            DaggerAppComponent.builder().create(this)
}
