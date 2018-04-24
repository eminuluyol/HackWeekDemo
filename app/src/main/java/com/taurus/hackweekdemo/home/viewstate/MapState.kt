package com.taurus.hackweekdemo.home.viewstate

import com.google.android.gms.common.GoogleApiAvailability

internal sealed class MapState

internal object Idle : MapState()

internal object ConnectionResultSuccess : MapState()

internal class ShowInfoDialog(val apiAvailability: GoogleApiAvailability, val isAvailable: Int) : MapState()

internal object GoogleServiceError : MapState()

internal object UpdateMarkerPosition : MapState()
