package com.taurus.hackweekdemo.notification.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.home.HomeActivity

class NotificationHelper(val context: Context) {
    fun create(title: String, subtitle: String, distance: Float, vin: String) {
        val builder = NotificationCompat.Builder(context.applicationContext, "notify_001")
        val intent = Intent(context.applicationContext, HomeActivity::class.java)
        intent.putExtra(VIN_NUMBER, vin)
        val pendingIntent = PendingIntent.getActivity(context.applicationContext, REQUEST_SELECT_LISTING, intent, 0)

        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(title)
        bigText.setBigContentTitle(title)
        bigText.setSummaryText(subtitle)

        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.drawable.ic_car_location)
        builder.setContentTitle(title)
        builder.setContentText(subtitle)
        builder.priority = Notification.PRIORITY_MAX
        builder.setStyle(bigText)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, builder.build())
    }

    companion object {
        const val REQUEST_SELECT_LISTING = 100
        const val VIN_NUMBER = "selected-vin-number"
    }
}