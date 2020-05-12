package com.architecture.domain

import android.app.Application
import android.content.IntentFilter
import android.os.Build
import com.architecture.ui.receiver.MyReceiver

class MyApplication : Application() {

    companion object{
        lateinit var component: MyComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerMyComponent.builder().appModule(AppModule(this)).build()

        val receiver = MyReceiver()
        val filter = IntentFilter("android.net.wifi.WIFI_STATE_CHANGED")
        registerReceiver(receiver, filter)
    }
}