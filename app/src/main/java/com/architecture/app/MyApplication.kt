package com.architecture.app

import android.app.Application
import android.content.IntentFilter
import com.architecture.BuildConfig
import com.architecture.core.logs.DebugTree
import com.architecture.core.logs.ReleaseTree
import com.architecture.domain.AppModule
import com.architecture.domain.DaggerMyComponent
import com.architecture.domain.MyComponent
import com.architecture.ui.receiver.MyReceiver
import timber.log.Timber

class MyApplication : Application() {

    companion object{
        lateinit var component: MyComponent
    }

    override fun onCreate() {
        super.onCreate()
        init()
        component = DaggerMyComponent.builder().appModule(AppModule(this)).build()
    }

    private fun init() {

        setupTimber()
        setUpReceiver()
    }

    private fun setUpReceiver() {
        val receiver = MyReceiver()
        val filter = IntentFilter("android.net.wifi.WIFI_STATE_CHANGED")
        registerReceiver(receiver, filter)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}