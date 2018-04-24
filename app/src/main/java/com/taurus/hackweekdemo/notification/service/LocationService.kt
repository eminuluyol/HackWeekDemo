package com.taurus.hackweekdemo.notification.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class LocationService : Service() {
    private lateinit var locationManager: LocationManager
    private lateinit var listener: MyLocationListener
    var previousBestLocation: Location? = null

    lateinit var intent: Intent

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
    }

    override fun onStart(intent: Intent, startId: Int) {
        handleStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handleStart(intent, startId)
        return START_NOT_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun handleStart(intent: Intent, startId: Int) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        listener = MyLocationListener()
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 1000f, listener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 1000f, listener)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun isBetterLocation(location: Location, currentBestLocation: Location?): Boolean {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }

        // Check whether the new location fix is newer or older
        val timeDelta = location.time - currentBestLocation.time
        val isSignificantlyNewer = timeDelta > TWO_MINUTES
        val isSignificantlyOlder = timeDelta < -TWO_MINUTES
        val isNewer = timeDelta > 0

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false
        }

        // Check whether the new location fix is more or less accurate
        val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200

        // Check if the old and new location are from the same provider
        val isFromSameProvider = isSameProvider(location.provider,
                currentBestLocation.provider)

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true
        } else if (isNewer && !isLessAccurate) {
            return true
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true
        }
        return false
    }


    /**
     * Checks whether two providers are the same
     */
    private fun isSameProvider(provider1: String?, provider2: String?): Boolean {
        return if (provider1 == null) {
            provider2 == null
        } else provider1 == provider2
    }


    override fun onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy()
        Log.v("STOP_SERVICE", "DONE")
        locationManager.removeUpdates(listener)
    }


    inner class MyLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.i(TAG, "Location changed: ${location.latitude}, ${location.longitude}")
            if (isBetterLocation(location, previousBestLocation)) {
                location.latitude
                location.longitude
                intent.putExtra("Latitude", location.latitude)
                intent.putExtra("Longitude", location.longitude)
                intent.putExtra("Provider", location.provider)
                sendBroadcast(intent)

            }
        }

        override fun onProviderDisabled(provider: String) {
            Toast.makeText(applicationContext, "Gps Disabled", Toast.LENGTH_SHORT).show()
        }


        override fun onProviderEnabled(provider: String) {
            Toast.makeText(applicationContext, "Gps Enabled", Toast.LENGTH_SHORT).show()
        }


        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

    }

    companion object {
        val TAG = LocationService::class.java.simpleName
        const val BROADCAST_ACTION = "Location update"
        private const val TWO_MINUTES = 1000 * 60 * 2
    }
}