package com.taurus.hackweekdemo.home.data

import com.google.gson.annotations.SerializedName

data class CarItemsWrapper(
        @SerializedName("placemarks") val carItems: List<CarItem>
)

data class CarItem(
        val photoUrl: String,
        @SerializedName("address") val address: String,
        @SerializedName("coordinates") val coordinates: List<Double>,
        @SerializedName("engineType") val engineType: String,
        @SerializedName("exterior") val exterior: String,
        @SerializedName("fuel") val fuel: Int,
        @SerializedName("interior") val interior: String,
        @SerializedName("name") val name: String,
        @SerializedName("vin") val vin: String
)

internal data class SelectedCarItem(val carItem: CarItem, val position: Int, val previousPosition: Int)
