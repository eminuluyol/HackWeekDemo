package com.taurus.hackweekdemo.notification

interface LocationServiceView {
    fun createNotification(image: String, title: String, subtitle: String, distance: Float)
}