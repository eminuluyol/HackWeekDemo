package com.taurus.hackweekdemo.core.utils.snackbar

import android.support.annotation.ColorRes
import android.support.annotation.IntegerRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.taurus.hackweekdemo.R

fun Snackbar.action(@IntegerRes actionRes: Int, @ColorRes color: Int, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, @ColorRes color: Int = R.color.colorAccent, listener: (View) -> Unit) {
    setAction(action, listener)
    setActionTextColor(ContextCompat.getColor(context , color))
}

fun Snackbar.textColor(color: Int) {
     view.findViewById<TextView>(android.support.design.R.id.snackbar_text).setTextColor(ContextCompat.getColor(context , color))
}

fun Snackbar.backgroundColor(color: Int) {
    view.setBackgroundColor(ContextCompat.getColor(context , color))
}

fun Snackbar.callBack(callback : Snackbar.Callback) {
    addCallback(callback)
}
