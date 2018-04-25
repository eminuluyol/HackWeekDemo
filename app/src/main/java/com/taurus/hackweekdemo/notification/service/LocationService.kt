package com.taurus.hackweekdemo.notification.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.location.PermissionUtils
import com.taurus.hackweekdemo.notification.LocationServicePresenter
import com.taurus.hackweekdemo.notification.LocationServiceView


class LocationService : Service(), LocationServiceView {
    private var locationManager: LocationManager? = null
    private var updateListener: LocationUpdateListener? = null
    private lateinit var intent: Intent
    private val presenter = LocationServicePresenter(this)
    private lateinit var notificationHelper: NotificationHelper
    private val binder = LocalBinder()
    private var isRequesting: Boolean = false

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
        notificationHelper = NotificationHelper(baseContext)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy()
        Log.v("STOP_SERVICE", "DONE")
        locationManager?.removeUpdates(updateListener)
    }

    @SuppressLint("WrongConstant")
    override fun createNotification(image: String, title: String, subtitle: String, distance: Float, vin: String) {
        notificationHelper.create(title, subtitle, distance, vin)
    }

    @SuppressLint("MissingPermission")
    fun findNearbyCars(carItems: List<CarItem>) {
        if (hasLocationPermissions() && !isRequesting && carItems.isNotEmpty()) {
            isRequesting = true
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            updateListener = LocationUpdateListener(this, { presenter.locationChanged(carItems, it) })
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 1000f, updateListener)
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 1000f, updateListener)
        }
    }

    private fun hasLocationPermissions(): Boolean = PermissionUtils.checkLocationPermission(this)

    companion object {
        val TAG = LocationService::class.java.simpleName
        const val BROADCAST_ACTION = "Location update"
        const val CHANNEL_ID = "channel_id"
    }

    inner class LocalBinder : Binder() {
        val serviceInstance: LocationService
            get() = this@LocationService
    }
}
