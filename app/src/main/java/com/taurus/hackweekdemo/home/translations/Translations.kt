package com.taurus.hackweekdemo.home.translations

import android.content.res.Resources

internal interface Translations {

    fun bind (resources: Resources)

    fun networkError() : String

    fun noInternetConnection() : String

    fun googleServiceError() : String

    fun permissionDenied() : String

    fun unbind()

}
