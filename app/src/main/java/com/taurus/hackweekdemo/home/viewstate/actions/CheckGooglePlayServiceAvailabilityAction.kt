package com.taurus.hackweekdemo.home.viewstate.actions

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.taurus.hackweekdemo.core.ViewStateLoop
import com.taurus.hackweekdemo.home.viewstate.*
import com.taurus.hackweekdemo.home.viewstate.commands.CheckGooglePlayServiceAvailabilityCommand
import io.reactivex.Observable

internal object CheckGooglePlayServiceAvailabilityAction : ViewStateLoop.ObservableAction<CheckGooglePlayServiceAvailabilityCommand, HomeScreenViewState> {

    override fun invoke(command: CheckGooglePlayServiceAvailabilityCommand): Observable<HomeScreenViewState.() -> HomeScreenViewState> {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val isAvailable = apiAvailability.isGooglePlayServicesAvailable(command.context)

        return when {
            isAvailable == ConnectionResult.SUCCESS -> singleMutation { copy(mapState = ConnectionResultSuccess) }
            apiAvailability.isUserResolvableError(isAvailable) -> singleMutation { copy(mapState = ShowInfoDialog(apiAvailability = apiAvailability, isAvailable = isAvailable)) }
            else -> singleMutation { copy(mapState = GoogleServiceError) }
        }

    }

}
