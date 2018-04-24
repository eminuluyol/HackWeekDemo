package com.taurus.hackweekdemo.network

import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.data.CarItemsWrapper
import io.reactivex.Single
import javax.inject.Inject

internal class CarFeedsService @Inject constructor(
        private val guidService: CarFeedsGuidService,
        private val dummyPhotoProvider: DummyPhotoProvider
) : GuidRepository {

    override fun allCarFeeds(): Single<List<CarItem>> {
        return guidService.getCarFeeds()
                .map { response ->
                    transformResponse(response)
                }
    }

    private fun transformResponse(response: CarItemsWrapper): List<CarItem> =
             response.carItems
                     .take(50)
                    .map { carItem ->
                        carItem.copy(photoUrl = dummyPhotoProvider.provideDummyPhoto())
                    }

}
