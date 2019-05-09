package com.sbingo.wanandroid_mvvm.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

object ExecutorUtils {

    val DISK_IO = Executors.newSingleThreadExecutor()

    val NETWORK_IO = Executors.newFixedThreadPool(5)

    fun main_thread(runnable: Runnable, delay: Long = 0) {
        Handler(Looper.getMainLooper())
            .postDelayed(runnable, delay)
    }
}