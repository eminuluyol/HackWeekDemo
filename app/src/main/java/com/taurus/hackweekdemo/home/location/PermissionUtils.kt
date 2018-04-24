package com.taurus.hackweekdemo.home.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

internal class PermissionUtils {
    companion object {
        fun checkLocationPermission(context: Context) =
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED

        fun checkIfPermissionsGranted(grantResult: IntArray) =
                grantResult.none { it != PackageManager.PERMISSION_GRANTED }
    }
}
