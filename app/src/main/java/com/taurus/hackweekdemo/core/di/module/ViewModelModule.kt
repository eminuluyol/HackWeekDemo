package com.taurus.hackweekdemo.core.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.taurus.hackweekdemo.home.HomeViewModel
import com.taurus.hackweekdemo.core.di.ViewModelFactory
import com.taurus.hackweekdemo.core.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeVieWModel(homeViewModel: HomeViewModel): ViewModel
}
