package com.sbingo.wanandroid_mvvm.utils

/**
 * Author: Sbingo666
 * Date:   2019/5/13
 */
object Listeners {
    interface PermissionListener {
        fun onGranted()

        fun onDenied(permissions: List<String>)

        fun onShowReason()
    }
}