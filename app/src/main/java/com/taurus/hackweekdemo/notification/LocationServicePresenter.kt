package com.taurus.hackweekdemo.notification

import android.location.Location
import com.taurus.hackweekdemo.home.data.CarItemsWrapper

class LocationServicePresenter(val view: LocationServiceView) {

    fun locationChanged(carItemsWrapper: CarItemsWrapper, location: Location) {
        val items = carItemsWrapper.carItems
        items.forEach { carItem ->
            val distanceInMeters = getDistanceInMeters(carItem.coordinates[1], carItem.coordinates[0], location)
            if (distanceInMeters <= 1000) {
                view.createNotification("", "Opel Astra", "Hey! There's a car near you", distanceInMeters, carItem.vin)
                return
            }
        }
    }

    private fun getDistanceInMeters(latitude: Double, longitude: Double, userLocation: Location): Float {
        val results = FloatArray(1)
        Location.distanceBetween(userLocation.latitude, userLocation.longitude, latitude, longitude, results)
        return results.first()
    }

}