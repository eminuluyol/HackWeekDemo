package com.taurus.hackweekdemo.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.extensions.setImageUrl
import kotlinx.android.synthetic.main.activity_detail_screen.*
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.os.Build

internal class DetailScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        makeStatusBarTransparent()
        val photoUrl = intent.getStringExtra(PHOTO_URL)
        val imageView = car_image_view
        val collapsingToolbarLayout = collapse_toolbar
        val appBarLayout = app_bar_layout

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange;
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = "AutoCheck"
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }

        })
        imageView.setImageUrl(photoUrl)
    }

    private fun makeStatusBarTransparent() {

        val window = window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

    }

    companion object {

        private const val PHOTO_URL = "photo_url"

        @JvmStatic
        fun newInstance(context: Context, photoUrl: String): Intent {
            val intent = Intent(context, DetailScreenActivity::class.java)
            intent.putExtra(PHOTO_URL, photoUrl)
            return intent
        }
    }
}
