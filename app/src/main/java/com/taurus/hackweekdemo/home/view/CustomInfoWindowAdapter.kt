package com.taurus.hackweekdemo.home.view

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

internal class CustomInfoWindowAdapter(val context: Activity) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(arg0: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {

        val info = LinearLayout(context)
        info.orientation = LinearLayout.VERTICAL

        val title = TextView(context)
        title.setTextColor(Color.BLACK)
        title.gravity = Gravity.CENTER
        title.setTypeface(null, Typeface.BOLD)
        title.text = "€ 4.000"

        val snippet = TextView(context)
        val padding = 4
        snippet.setPadding(padding, 0, padding, 0)
        snippet.setTextColor(Color.GRAY)
        snippet.text = marker.snippet

        info.addView(title)
        info.addView(snippet)

        return info
    }
}
