package com.taurus.hackweekdemo.core.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

const val ANIMATION_DURATION = 300L

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.setImageUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}

fun View.fadeIn(duration: Long = ANIMATION_DURATION) {
    this.alpha = 0F
    this.visibility = View.VISIBLE
    this.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(null)
}
