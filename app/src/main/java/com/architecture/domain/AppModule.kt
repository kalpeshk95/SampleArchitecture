package com.architecture.domain

import android.content.Context
import android.content.SharedPreferences
import com.architecture.data.sharedpref.SharedPref
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        return@single getPref(androidContext())
    }
}

fun getPref(context: Context): SharedPreferences {
    return SharedPref.getPref(context)
}
