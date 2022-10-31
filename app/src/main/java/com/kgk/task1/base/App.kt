package com.kgk.task1.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.kgk.task1.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    networkModule, viewModelModule, prefModule
                )
            )
        }
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Log.d(Application::class.simpleName, "onActivityCreated " + p0::class.simpleName)
    }

    override fun onActivityStarted(p0: Activity) {
        Log.d(Application::class.simpleName, "onActivityStarted " + p0::class.simpleName)
    }

    override fun onActivityResumed(p0: Activity) {
        Log.d(Application::class.simpleName, "onActivityResumed " + p0::class.simpleName)
    }

    override fun onActivityPaused(p0: Activity) {
        Log.d(Application::class.simpleName, "onActivityPaused " + p0::class.simpleName)
    }

    override fun onActivityStopped(p0: Activity) {
        Log.d(Application::class.simpleName, "onActivityStopped " + p0::class.simpleName)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        Log.d(Application::class.simpleName, "onActivitySaveInstanceState " + p0::class.simpleName)
    }

    override fun onActivityDestroyed(p0: Activity) {
        Log.d(Application::class.simpleName, "onActivityDestroyed")
    }
}