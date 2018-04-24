package com.taurus.hackweekdemo.home.view

import android.support.design.widget.Snackbar
import android.view.View
import com.taurus.hackweekdemo.core.utils.snackbar.NotificationSnackbar
import com.taurus.hackweekdemo.core.utils.snackbar.callBack
import com.taurus.hackweekdemo.core.utils.snackbar.textColor
import com.taurus.hackweekdemo.home.translations.Translations
import com.taurus.hackweekdemo.home.viewstate.*
import com.taurus.hackweekdemo.home.viewstate.commands.UpdateSnackbarCommand

internal class SnackbarViewContainer private constructor(
        private val notificationSnackbar: NotificationSnackbar,
        private val translations: Translations,
        private val commandProcessor: CommandProcessor
) {

    companion object {
        operator fun invoke(
                notificationSnackbar: NotificationSnackbar,
                translations: Translations,
                commandProcessor: CommandProcessor
        ): SnackbarViewContainer {
            return SnackbarViewContainer(
                    notificationSnackbar = notificationSnackbar,
                    translations = translations,
                    commandProcessor = commandProcessor
            )
        }
    }

    fun bind(view: View) {
        notificationSnackbar.bind(view)
    }

    private var prevSnackbarState: SnackbarState = Hidden

    fun updateViewState(state: HomeScreenViewState) {
        if (state.snackbarState == prevSnackbarState) {
            return
        }
        this.prevSnackbarState = state.snackbarState

        when (state.snackbarState) {
            is Hidden -> return
            is GenericError -> {
                val message = translations.networkError()
                showSnackbar(message)
            }
            is NoInternetConnection -> {
                val message = translations.noInternetConnection()
                showSnackbar(message)
            }
            is ServiceError -> {
                val message = translations.googleServiceError()
                showSnackbar(message)
            }
            is PermissionDenied -> {
                val message = translations.permissionDenied()
                showSnackbar(message)
            }
        }
    }

    private fun showSnackbar(message: String) {
        notificationSnackbar.snack(message) {
            textColor(android.R.color.white)
            callBack(dismissCallback)
        }
    }

    private val dismissCallback = object : Snackbar.Callback() {
        override fun onDismissed(snackbar: Snackbar, event: Int) {
            super.onDismissed(snackbar, event)
            commandProcessor.process(UpdateSnackbarCommand(newState = Hidden))
        }
    }

    fun unbind() {
        notificationSnackbar.unbind()
    }
}
