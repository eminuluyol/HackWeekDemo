package com.taurus.hackweekdemo.notification.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import com.taurus.hackweekdemo.home.data.CarItemsWrapper
import com.taurus.hackweekdemo.notification.LocationServicePresenter
import com.taurus.hackweekdemo.notification.LocationServiceView


class LocationService : Service(), LocationServiceView {
    private lateinit var locationManager: LocationManager
    private lateinit var updateListener: LocationUpdateListener
    private lateinit var carItemsWrapper: CarItemsWrapper

    lateinit var intent: Intent
    val presenter = LocationServicePresenter(this)
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
        notificationHelper = NotificationHelper(baseContext)
        //TODO get dataset from intent
        readData()
    }

    override fun onStart(intent: Intent, startId: Int) {
        handleStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handleStart(intent, startId)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy()
        Log.v("STOP_SERVICE", "DONE")
        locationManager.removeUpdates(updateListener)
    }

    @SuppressLint("WrongConstant")
    override fun createNotification(image: String, title: String, subtitle: String, distance: Float, vin: String) {
        notificationHelper.create(title, subtitle, distance, vin)
    }

    private fun readData() {
        val json = applicationContext.assets.open("locations.json").bufferedReader().use {
            it.readText()
        }
        carItemsWrapper = Gson().fromJson<CarItemsWrapper>(json, CarItemsWrapper::class.java)
    }

    @SuppressLint("MissingPermission")
    private fun handleStart(intent: Intent, startId: Int) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        updateListener = LocationUpdateListener(intent, startId, this, { presenter.locationChanged(carItemsWrapper, it) })
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 1000f, updateListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 1000f, updateListener)
    }

    companion object {
        val TAG = LocationService::class.java.simpleName
        const val BROADCAST_ACTION = "Location update"
        const val CHANNEL_ID = "channel_id"
    }
}