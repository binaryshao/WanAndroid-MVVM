package com.sbingo.wanandroid_mvvm


import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger

/**
 * Author: Sbingo666
 * Date:   2019/4/3
 */
class WanApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogger()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    private fun initLogger() {
        val lever = if (BuildConfig.DEBUG) LogLevel.FULL else LogLevel.NONE
        Logger.init("Sbingo666")
            .methodCount(0)
            .hideThreadInfo()
            .logLevel(lever)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Logger.d(activity.componentName.className + " onCreated")
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            Logger.d(activity.componentName.className + " onResumed")
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Logger.d(activity.componentName.className + " onDestroyed")
        }
    }


}