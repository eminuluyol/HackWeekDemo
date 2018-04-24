package com.taurus.hackweekdemo.core.utils.snackbar

import android.support.design.widget.Snackbar
import android.view.View

interface Notificationbar {

    fun bind(view: View)

    fun snack(message: String, length: Int = Snackbar.LENGTH_LONG, function: Snackbar.() -> Unit)

    fun unbind()
}
