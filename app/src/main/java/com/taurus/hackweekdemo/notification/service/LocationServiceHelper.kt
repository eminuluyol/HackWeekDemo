package com.taurus.hackweekdemo.notification.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log


class LocationServiceHelper(private val activity: Activity) {
    var locationService: LocationService? = null
    private var connection: ServiceConnection? = null
    private val intent: Intent

    init {
        initConnection()
        intent = Intent(activity, LocationService::class.java)
        activity.startService(intent)
        activity.bindService(intent, connection, Context.BIND_AUTO_CREATE)

    }

    private fun initConnection() {
        connection = object : ServiceConnection {
            override fun onServiceConnected(className: ComponentName,
                                            service: IBinder) {
                Log.d(TAG, "OnServiceConnected")
                val binder = service as LocationService.LocalBinder
                locationService = binder.serviceInstance
            }

            override fun onServiceDisconnected(arg0: ComponentName) {
                Log.d(TAG, "OnServiceDisconnected")
            }
        }
    }

    fun unbind() {
        connection?.let { activity.unbindService(it) }
        locationService?.stopService(intent)
    }

    companion object {
        val TAG = LocationServiceHelper::class.java.simpleName
    }
}
