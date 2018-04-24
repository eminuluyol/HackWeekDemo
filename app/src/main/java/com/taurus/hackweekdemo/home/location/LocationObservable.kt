package com.taurus.hackweekdemo.home.location

import android.Manifest
import android.content.Context
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

internal class LocationObservable(private val context: Context) {

    private val locationSubject = PublishSubject.create<LocationData>()

    /**
     * Location Request and Client
     */
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = createLocationRequest()

    /**
     * Location Settings
     */
    private val locationSettingsBuilder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    private val settingsClient = LocationServices.getSettingsClient(context)

    val locations: Observable<LocationData>
        get() = locationSubject
                .doOnSubscribe {
                    checkPermissionGranted()
                }
                .doOnDispose {
                    fusedLocationClient.removeLocationUpdates(fineLocationCallback)
                }

    /**
     * Callback
     */
    private val fineLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            locationSubject.onNext(LocationData.success(result?.lastLocation))
        }
    }

    /**
     * Checks runtime permission first.
     * Then check if GPS settings is enabled by user
     * If all good, then start listening user location
     * and update livedata
     */
    fun startLocationUpdates() {
        if (!PermissionUtils.checkLocationPermission(context)) {
            locationSubject.onNext(LocationData.error(IllegalStateException("Permission  not granted")))
            return
        }
        val settingsTask = settingsClient.checkLocationSettings(locationSettingsBuilder.build())

        settingsTask.addOnSuccessListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let { LocationData.success(location) }
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, fineLocationCallback, null)
        }

        settingsTask.addOnFailureListener {
            val result = if (it is ResolvableApiException) LocationData.settingsRequired(it) else LocationData.error(it)
            locationSubject.onNext(result)
        }
    }

    private fun checkPermissionGranted() {
        if (!PermissionUtils.checkLocationPermission(context)) {
            locationSubject.onNext(LocationData.permissionRequired(listOf(Manifest.permission.ACCESS_FINE_LOCATION)))
        }
    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }


}
