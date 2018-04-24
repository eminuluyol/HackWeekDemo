package com.taurus.hackweekdemo.core.di.module

import android.content.Context
import com.taurus.hackweekdemo.core.HackWeekDemoApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(movieApp: HackWeekDemoApp): Context = movieApp.applicationContext

}
