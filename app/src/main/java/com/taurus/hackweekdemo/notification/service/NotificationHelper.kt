package com.taurus.hackweekdemo.notification.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.taurus.hackweekdemo.R

class NotificationHelper(val context: Context) {
    fun create(title: String, subtitle: String) {
        val builder = NotificationCompat.Builder(context.applicationContext, "notify_001")
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

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, builder.build())
    }
}