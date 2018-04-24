package com.taurus.hackweekdemo.notification

import android.location.Location
import com.taurus.hackweekdemo.home.data.CarItem

class LocationServicePresenter(val view: LocationServiceView) {

    private var lastCar: CarItem? = null

    fun locationChanged(items: List<CarItem>, location: Location) {
        items.forEach { carItem ->
            val distanceInMeters = getDistanceInMeters(carItem.coordinates[1], carItem.coordinates[0], location)
            if (distanceInMeters <= 1000 && lastCar != carItem) {
                view.createNotification("", "Ford Mustang", "Hey! There's a car near you", distanceInMeters, carItem.vin)
                lastCar = carItem
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
