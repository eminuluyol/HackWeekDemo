package com.taurus.hackweekdemo.home.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.SupportMapFragment
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.base.BaseFragment
import com.taurus.hackweekdemo.core.utils.permission.RxPermissionHandler
import com.taurus.hackweekdemo.core.utils.rxjava.SchedulingStrategy
import com.taurus.hackweekdemo.core.utils.snackbar.NotificationSnackbar
import com.taurus.hackweekdemo.home.HomeScreenPresenter
import com.taurus.hackweekdemo.home.HomeViewModel
import com.taurus.hackweekdemo.home.Presenter
import com.taurus.hackweekdemo.home.location.LocationObservable
import com.taurus.hackweekdemo.home.navigator.Navigator
import com.taurus.hackweekdemo.home.translations.Translations
import com.taurus.hackweekdemo.home.viewstate.CommandProcessor
import com.taurus.hackweekdemo.home.viewstate.commands.CheckGooglePlayServiceAvailabilityCommand
import com.taurus.hackweekdemo.notification.service.LocationServiceHelper
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
    private lateinit var serviceHelper: LocationServiceHelper

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
        val locationObservable = LocationObservable(context!!)
        mapViewContainer = MapViewContainer(commandProcessor, locationObservable, RxPermissionHandler(activity!!), schedulingStrategy)
        mapViewContainer.bind(activity, mapFragment)

        navigator.bind(activity)
        activity?.let { serviceHelper = LocationServiceHelper(it) }
        val savedSearchAdapter = CarFeedsAdapter(navigator)
        val rootContainer = HomeScreenViewContainer(
                view,
                savedSearchAdapter,
                commandProcessor,
                serviceHelper
        )

        presenter = HomeScreenPresenter(
                schedulingStrategy,
                viewModel
        ).apply {
            bind(
                    homeScreenViewContainer = rootContainer,
                    snackbarViewContainer = snackbarViewContainer,
                    mapViewContainer = mapViewContainer
            )
        }

    }

    override fun onDestroyView() {
        presenter.unbind()
        snackbarViewContainer.unbind()
        mapViewContainer.unbind()
        navigator.unbind()
        translations.unbind()
        serviceHelper.unbind()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOCATION_SETTINGS && resultCode == Activity.RESULT_OK) {
            mapViewContainer.startLocationUpdate()
        }
    }

    companion object {
        fun newInstance() = HomeScreenFragment()
    }

}
