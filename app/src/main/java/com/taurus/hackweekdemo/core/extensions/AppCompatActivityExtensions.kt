package com.taurus.hackweekdemo.core.extensions

import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment


fun AppCompatActivity.replaceFragment(layoutId: Int, fragment: Fragment) {
    supportFragmentManager.transaction { replace(layoutId, fragment) }
}
