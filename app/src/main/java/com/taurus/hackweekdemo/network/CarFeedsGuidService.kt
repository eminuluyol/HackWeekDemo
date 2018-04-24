package com.taurus.hackweekdemo.network

import com.taurus.hackweekdemo.home.data.CarItemsWrapper
import io.reactivex.Single
import retrofit2.http.GET

internal interface CarFeedsGuidService {

    @GET("/wunderbucket/locations.json")
    fun getCarFeeds(): Single<CarItemsWrapper>

}
