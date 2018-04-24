package com.taurus.hackweekdemo.home.view

import android.Manifest
import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.utils.permission.RxPermissionHandler
import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.data.SelectedCarItem
import com.taurus.hackweekdemo.home.location.LocationData
import com.taurus.hackweekdemo.home.location.LocationObservable
import com.taurus.hackweekdemo.home.viewstate.*
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateSnackbarCommand

private const val TAG = "MapViewContainer"

internal class MapViewContainer constructor(
        private val commandProcessor: CommandProcessor,
        private val locationObservable: LocationObservable,
        private val rxPermissionHandler: RxPermissionHandler,
        private val schedulingStrategy: SchedulingStrategy
) : HomeScreenView {

    private var activity: Activity? = null
    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private val markerList = ArrayList<Marker>()

    fun bind(activity: FragmentActivity?, mapFragment: SupportMapFragment?) {
        this.activity = activity
        this.mapFragment = mapFragment

        locationObservable
                .locations
                .compose(schedulingStrategy.apply())
                .subscribe { it ->
                    when (it.status) {
                        LocationData.Status.PERMISSION_REQUIRED -> {
                            rxPermissionHandler
                                    .ensure(Manifest.permission.ACCESS_FINE_LOCATION)
                                    .subscribe { permission ->
                                        if (permission.granted) {
                                            locationObservable.startLocationUpdates()
                                        } else {
                                            commandProcessor.process(UpdateSnackbarCommand(PermissionDenied))
                                        }
                                    }
                        }
                        LocationData.Status.ENABLE_SETTINGS -> {
                            //TODO haven't supported yet.
                        }
                        LocationData.Status.LOCATION_SUCCESS -> {
                            Log.i(TAG, "You got it!")
                        }
                        else -> Log.e(TAG, "Unsupported case")
                    }
                }
    }

    override fun updateViewState(viewState: HomeScreenViewState) {

        when (viewState.mapState) {
            is Idle -> return
            is ConnectionResultSuccess -> {
                mapFragment?.getMapAsync { map ->
                    googleMap = map
                    drawPlaceMarkersOnMap(map, viewState.carItems)
                }
            }
            is ShowInfoDialog -> {
                showInformationDialog(viewState.mapState.apiAvailability, viewState.mapState.isAvailable)
            }
            is GoogleServiceError -> {
                showGoogleServiceError()
            }
            is UpdateMarkerPosition -> {
                viewState.selectedCarItem?.let {
                    updateSelectedPinPosition(it)
                }
            }
        }

    }

    private fun updateSelectedPinPosition(selectedCarItem: SelectedCarItem) {
        val nextDestination = LatLng(selectedCarItem.carItem.coordinates[1], selectedCarItem.carItem.coordinates[0])
        val marker = markerList[selectedCarItem.position]
        val previousMarker = markerList[selectedCarItem.previousPosition]

        marker.remove()
        val activatedMarker = googleMap?.addMarker(MarkerOptions().position(nextDestination)
                .title(selectedCarItem.carItem.name)
                .snippet(selectedCarItem.carItem.address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location_active)))
        markerList.remove(marker)
        markerList.add(selectedCarItem.position, activatedMarker!!)

        previousMarker.remove()
        val deactivatedMarker = googleMap?.addMarker(MarkerOptions().position(previousMarker.position)
                .title(selectedCarItem.carItem.name)
                .snippet(selectedCarItem.carItem.address)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location_deactive)))
        markerList.remove(previousMarker)
        markerList.add(selectedCarItem.previousPosition, previousMarker!!)

        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(nextDestination, 16f))
        deactivatedMarker?.hideInfoWindow()
        activatedMarker.showInfoWindow()
    }

    private fun showGoogleServiceError() {
        commandProcessor.process(UpdateSnackbarCommand(ServiceError))
    }

    private fun showInformationDialog(apiAvailability: GoogleApiAvailability, isAvailable: Int) {
        activity?.let {
            val dialog = apiAvailability.getErrorDialog(activity, isAvailable, 0)
            dialog.show()
        }
    }

    private fun drawPlaceMarkersOnMap(googleMap: GoogleMap, carItems: List<CarItem>) {

        // Add markers in Map
        for (marker in carItems) {

            val place = LatLng(marker.coordinates[1], marker.coordinates[0])
            markerList.add(googleMap.addMarker(MarkerOptions().position(place)
                    .title(marker.name)
                    .snippet(marker.address)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location_deactive))))

        }

        //move the camera
        if (carItems.isNotEmpty()) {
            val item = carItems[0]
            val firstPlace = LatLng(item.coordinates[1], item.coordinates[0])
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPlace, 11.1f))
        }

        val adapter = CustomInfoWindowAdapter(activity!!)
        googleMap.setInfoWindowAdapter(adapter);

    }

    fun unbind() {
        activity = null
    }

}
