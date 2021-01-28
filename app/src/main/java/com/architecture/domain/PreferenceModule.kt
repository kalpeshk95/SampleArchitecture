package com.architecture.domain

import org.koin.dsl.module

val preferencesModule = module {

//    single {
//        providePreferences(androidContext())
//    }
//
//    single {
//        Prefs(prefs = get())
//    }
}

//private fun providePreferences(context: Context): SharedPreferences =
//    context.getSharedPreferences(C.PREFS_FILE_NAME, Context.MODE_PRIVATE)