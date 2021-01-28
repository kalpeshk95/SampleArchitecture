package com.architecture.domain

import com.architecture.data.remote.RemoteRepository
import com.architecture.data.roomdb.RoomRepository
import com.architecture.data.sharedpref.SharedPreferencesRepository
import org.koin.dsl.module

val repoModule = module {

    single { RemoteRepository(get(), get()) }
    single { RoomRepository(get()) }
    single { SharedPreferencesRepository(get()) }
}