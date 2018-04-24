package com.taurus.hackweekdemo.home.view

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.SupportMapFragment
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.base.BaseFragment
import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.core.utils.snackbar.NotificationSnackbar
import com.taurus.hackweekdemo.home.HomeScreenPresenter
import com.taurus.hackweekdemo.home.HomeViewModel
import com.taurus.hackweekdemo.home.Presenter
import com.taurus.hackweekdemo.home.location.LocationData
import com.taurus.hackweekdemo.home.location.LocationObservable
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.translations.Translations
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.commands.CheckGooglePlayServiceAvailabilityCommand
import javax.inject.Inject


internal class HomeScreenFragment : BaseFragment<HomeViewModel>() {

    @Inject
    internal lateinit var schedulingStrategy: SchedulingStrategy
    @Inject
    internal lateinit var notificationSnackbar: NotificationSnackbar
    @Inject
    internal lateinit var translations: Translations
    @Inject
    internal lateinit var commandProcessor: CommandProcessor
    @Inject
    internal lateinit var navigator: Navigator

    private lateinit var presenter: Presenter
    private lateinit var snackbarViewContainer: SnackbarViewContainer
    private lateinit var mapViewContainer: MapViewContainer

    override fun getLayoutResId() = R.layout.fragment_home_screen

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commandProcessor.process(CheckGooglePlayServiceAvailabilityCommand(context!!))
        translations.bind(context!!.resources)

        snackbarViewContainer = SnackbarViewContainer(notificationSnackbar, translations, commandProcessor)
        snackbarViewContainer.bind(view.rootView)

        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapViewContainer = MapViewContainer(commandProcessor)
        mapViewContainer.bind(activity, mapFragment)

        navigator.bind(this.activity)
        val savedSearchAdapter = CarFeedsAdapter(commandProcessor, navigator)

        val rootContainer = HomeScreenViewContainer(
                view,
                savedSearchAdapter,
                commandProcessor
        )

        presenter = HomeScreenPresenter(
                schedulingStrategy,
                viewModel
        ).apply {
            bind(
                    homeScreenViewContainer = rootContainer,
                    snackbarViewContainer = snackbarViewContainer
            )
        }

        val locationObservable = LocationObservable(context!!)
        locationObservable
                .locations
                .subscribe { it ->
                    when(it.status) {
                        LocationData.Status.PERMISSION_REQUIRED -> {}
                        LocationData.Status.ENABLE_SETTINGS -> {}
                        LocationData.Status.LOCATION_SUCCESS -> {}
                    }
                }

    }

    override fun onStart() {
        super.onStart()
        mapViewContainer.connectGoogleApiClient()
    }

    override fun onPause() {
        mapViewContainer.stopLocationUpdates()
        super.onPause()
    }


    override fun onDestroyView() {
        presenter.unbind()
        snackbarViewContainer.unbind()
        mapViewContainer.unbind()
        navigator.unbind()
        translations.unbind()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

}
