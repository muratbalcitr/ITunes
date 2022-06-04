package com.m2lifeApps.hepsiburada.core.common

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class UiThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        handler.post(command)
    }
}
