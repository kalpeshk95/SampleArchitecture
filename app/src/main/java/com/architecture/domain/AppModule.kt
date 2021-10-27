package com.architecture.domain

import android.content.Context
import android.content.SharedPreferences
import com.architecture.data.sharedpref.Prefs
import com.architecture.utils.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        providePreferences(androidContext())
    }

    single {
        Prefs(prefs = get())
    }
}

@Synchronized
private fun providePreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(Constant.PREFS_FILE_NAME, Context.MODE_PRIVATE)
