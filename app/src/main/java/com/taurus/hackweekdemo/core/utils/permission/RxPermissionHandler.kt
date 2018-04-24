package com.taurus.hackweekdemo.core.utils.permission

import android.support.v4.app.FragmentActivity
import com.taurus.hackweekdemo.core.utils.permission.PermissionHandler.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

internal class RxPermissionHandler(activity: FragmentActivity) : PermissionHandler {

    private val rxPermissions = RxPermissions(activity)

    override fun ensure(vararg permissions: String): Observable<PermissionHandler.Permission> =
            rxPermissions
                    .requestEach(*permissions)
                    .map { permission -> Permission(permission.granted, !permission.shouldShowRequestPermissionRationale) }

}
