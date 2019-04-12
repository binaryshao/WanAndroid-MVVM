package com.sbingo.wanandroid_mvvm.utils

import java.util.concurrent.Executors

object ExecutorUtils {

    val DISK_IO = Executors.newSingleThreadExecutor()

    val NETWORK_IO = Executors.newFixedThreadPool(5)

}