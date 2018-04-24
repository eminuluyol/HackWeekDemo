package com.taurus.hackweekdemo.core.di.module

import com.taurus.hackweekdemo.core.di.scope.FragmentScope
import com.taurus.hackweekdemo.home.view.HomeScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun homeScreenFragment(): HomeScreenFragment
}
