package com.taurus.hackweekdemo.home.viewstate

internal sealed class SnackbarState

internal object Hidden : SnackbarState()

internal object GenericError : SnackbarState()

internal object NoInternetConnection : SnackbarState()

internal object ServiceError : SnackbarState()
