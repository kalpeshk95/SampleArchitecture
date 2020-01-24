package com.architecture.domain

import android.app.Application

class MyApplication : Application() {

    companion object{
        lateinit var component: MyComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerMyComponent.builder().appModule(AppModule(this)).build()
    }
}