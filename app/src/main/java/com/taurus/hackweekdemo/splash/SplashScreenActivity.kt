package com.taurus.hackweekdemo.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.taurus.hackweekdemo.home.HomeActivity
import io.reactivex.Completable
import io.reactivex.Maybe
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Maybe.empty<Any>() // returns maybe instance that calls onComplete right away
                .delay(3, TimeUnit.SECONDS) // posting delay of 3 seconds
                .doOnComplete(this::launchHomeActivity)
                .subscribe()

    }

    private fun launchHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
