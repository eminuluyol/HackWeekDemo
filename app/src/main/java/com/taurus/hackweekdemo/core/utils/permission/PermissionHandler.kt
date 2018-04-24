package com.taurus.hackweekdemo.core.utils.permission

import io.reactivex.Observable

interface PermissionHandler {

    fun ensure(vararg permissions: String): Observable<Permission>

    data class Permission(val granted: Boolean, val deniedForever: Boolean)

}
