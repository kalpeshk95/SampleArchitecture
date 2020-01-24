package com.architecture.domain

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule internal constructor(var context: MyApplication){

    @Provides
    @Singleton
    fun getContext() : Context {
        return context
    }
}