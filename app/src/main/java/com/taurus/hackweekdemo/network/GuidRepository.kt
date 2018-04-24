package com.taurus.hackweekdemo.network

import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.data.CarItemsWrapper
import io.reactivex.Maybe
import io.reactivex.Single

internal interface GuidRepository {

    fun allCarFeeds() : Single<List<CarItem>>
}
