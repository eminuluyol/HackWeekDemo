package com.taurus.hackweekdemo.notification.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.gson.Gson
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.home.data.CarItemsWrapper
import com.taurus.hackweekdemo.notification.LocationServicePresenter
import com.taurus.hackweekdemo.notification.LocationServiceView
import android.app.NotificationChannel
import android.os.Build


class LocationService : Service(), LocationServiceView {
    private lateinit var locationManager: LocationManager
    private lateinit var updateListener: LocationUpdateListener
    private lateinit var carItemsWrapper: CarItemsWrapper

    lateinit var intent: Intent
    val presenter = LocationServicePresenter(this)

    override fun onCreate() {
        super.onCreate()
        intent = Intent(BROADCAST_ACTION)
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
    override fun createNotification(image: String, title: String, subtitle: String, distance: Float) {
        val builder = NotificationCompat.Builder(applicationContext, "notify_001")
//        val ii = Intent(applicationContext, RootActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, ii, 0)

        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(title)
        bigText.setBigContentTitle(title)
        bigText.setSummaryText(subtitle)

//        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.drawable.ic_car_location)
        builder.setContentTitle(title)
        builder.setContentText(subtitle)
        builder.priority = Notification.PRIORITY_MAX
        builder.setStyle(bigText)

        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, builder.build())
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