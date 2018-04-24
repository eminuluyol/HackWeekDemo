package com.taurus.hackweekdemo.core.di.component

import com.taurus.hackweekdemo.core.HackWeekDemoApp
import com.taurus.hackweekdemo.core.di.module.ActivityBuilderModule
import com.taurus.hackweekdemo.core.di.module.AppModule
import com.taurus.hackweekdemo.core.di.module.FragmentBuilderModule
import com.taurus.hackweekdemo.core.utils.network.NetworkMonitorModule
import com.taurus.hackweekdemo.home.HomeScreenModule
import com.taurus.hackweekdemo.network.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    HomeScreenModule::class,
    NetworkMonitorModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class])
interface AppComponent : AndroidInjector<HackWeekDemoApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<HackWeekDemoApp>()
}
