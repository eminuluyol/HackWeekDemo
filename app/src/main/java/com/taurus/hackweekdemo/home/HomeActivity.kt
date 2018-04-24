package com.taurus.hackweekdemo.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.R.id.fragment_container
import com.taurus.hackweekdemo.core.extensions.replaceFragment
import com.taurus.hackweekdemo.home.view.HomeScreenFragment

internal class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(fragment_container, HomeScreenFragment.newInstance())
    }

}
