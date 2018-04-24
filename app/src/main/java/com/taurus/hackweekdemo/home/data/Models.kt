package com.taurus.hackweekdemo.home.data

import com.google.gson.annotations.SerializedName

internal data class CarItemsWrapper(
        @SerializedName("placemarks") val carItems: List<CarItem>
)

internal data class CarItem(
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
