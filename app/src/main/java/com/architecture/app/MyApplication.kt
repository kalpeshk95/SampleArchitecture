package com.architecture.app

import android.app.Application
import android.content.IntentFilter
import com.architecture.BuildConfig
import com.architecture.core.logs.DebugTree
import com.architecture.core.logs.ReleaseTree
import com.architecture.domain.*
import com.architecture.ui.receiver.MyReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setUpReceiver()
        setupDI()
    }

    private fun setupDI() {

        startKoin {

            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR)
            }

            androidContext(this@MyApplication)

            modules(
                listOf(
                    preferencesModule,
                    appModule,
                    repoModule,
                    retrofitModule,
                    databaseModule,
                    viewModelModule
                )
            )
        }
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