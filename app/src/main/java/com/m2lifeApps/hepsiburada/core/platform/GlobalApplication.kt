package com.m2lifeApps.hepsiburada.core.platform

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
 import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
     override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        injectMultiDex()
    }

    private fun injectMultiDex() {
        MultiDex.install(this)
    }

    companion object {
        lateinit var appContext: Context

    }
}
