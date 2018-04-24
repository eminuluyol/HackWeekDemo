package com.taurus.hackweekdemo.core.utils.snackbar

import android.support.design.widget.Snackbar
import android.view.View
import javax.inject.Inject

class NotificationSnackbar @Inject constructor() : Notificationbar {
    private var rootView: View? = null

    override fun bind(view: View) {
        this.rootView = view
    }

    override fun snack(message: String, length: Int, function: Snackbar.() -> Unit) {
        rootView?.let {
            val snack = Snackbar.make(it, message, length)
            snack.function()
            if (!snack.isShownOrQueued) {
                snack.show()
            }
        }
    }

    override fun unbind() {
        rootView = null
    }

}
