package com.taurus.hackweekdemo.home.translations

import android.content.res.Resources
import com.taurus.hackweekdemo.R

internal class HomeScreenTranslations : Translations {

    private var resources : Resources? = null

    override fun bind(resources: Resources) {
        this.resources = resources
    }

    override fun networkError() = resources?.let {
        resources!!.getString(R.string.network_error)
    } ?: ""

    override fun noInternetConnection()  = resources?.let {
        resources!!.getString(R.string.no_internet_connection)
    } ?: ""

    override fun googleServiceError() = resources?.let {
        resources!!.getString(R.string.google_service_error)
    } ?: ""

    override fun unbind() {
        resources = null
    }
}
