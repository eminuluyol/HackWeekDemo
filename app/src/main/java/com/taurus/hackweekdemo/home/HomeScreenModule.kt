package com.taurus.hackweekdemo.home

import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.core.utils.snackbar.NotificationSnackbar
import com.taurus.hackweekdemo.home.navigator.HomeScreenNavigator
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.translations.HomeScreenTranslations
import com.taurus.hackweekdemo.home.translations.Translations
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.CommandRouter
import com.taurus.hackweekdemo.home.viewstate.CommandWrapper
import com.taurus.hackweekdemo.home.viewstate.HomeScreenViewState
import com.taurus.hackweekdemo.home.viewstate.actions.CheckGooglePlayServiceAvailabilityAction
import com.taurus.hackweekdemo.home.viewstate.actions.NewItemsAction
import com.taurus.hackweekdemo.home.viewstate.actions.UpdateNetworkConnectivityAction
import com.taurus.hackweekdemo.home.viewstate.actions.UpdateSnackbarAction
import com.taurus.hackweekdemo.home.viewstate.commands.CheckGooglePlayServiceAvailabilityCommand
import com.taurus.hackweekdemo.home.viewstate.commands.HomeScreenCommand
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
internal class HomeScreenModule {

    @Provides
    @Singleton
    internal fun provideCommandWrapper() = CommandWrapper()

    @Provides
    internal fun provideCommandProcessor(commandWrapper: CommandWrapper): CommandProcessor = commandWrapper

    @Provides
    @Singleton
    internal fun provideCommandRouter(): CommandRouter = CommandRouter(
            UpdateNetworkConnectivityAction,
            UpdateSnackbarAction,
            NewItemsAction,
            CheckGooglePlayServiceAvailabilityAction
    )

    @Provides
    @Singleton
    internal fun provideStateLoop(commandWrapper: CommandWrapper) = ViewStateLoop<HomeScreenCommand, HomeScreenViewState>(commandWrapper.commandStream)

    @Provides
    internal fun provideNotificationSnackbar(): NotificationSnackbar {
        return NotificationSnackbar()
    }

    @Provides
    internal fun provideSchedulingStrategy(): SchedulingStrategy {
        return SchedulingStrategy(Schedulers.io(), AndroidSchedulers.mainThread())
    }

    @Provides
    @Singleton
    internal fun provideHomeScreenTranslations(): Translations {
        return HomeScreenTranslations()
    }

    @Provides
    @Singleton
    internal fun provideHomeScreenNavigator(): Navigator {
        return HomeScreenNavigator()
    }

}
