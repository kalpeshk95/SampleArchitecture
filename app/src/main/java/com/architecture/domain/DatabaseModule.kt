package com.architecture.domain

import android.content.Context
import androidx.room.Room
import com.architecture.data.roomdb.LocalDao
import com.architecture.data.roomdb.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<LocalDatabase> {
        getLocalDatabase(androidContext = androidContext())
    }

    single { getLocalDao(get()) }
}

private fun getLocalDao(localDatabase: LocalDatabase): LocalDao = localDatabase.localDao()

private fun getLocalDatabase(androidContext: Context): LocalDatabase {
    return Room
        .databaseBuilder(androidContext, LocalDatabase::class.java, "local_database.db")
        .fallbackToDestructiveMigration()
        .fallbackToDestructiveMigrationOnDowngrade()
        .fallbackToDestructiveMigrationFrom(
            1,
            2
        )
        .build()
}