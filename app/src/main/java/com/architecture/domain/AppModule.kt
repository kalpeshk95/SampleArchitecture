package com.architecture.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.architecture.app.MyApplication
import com.architecture.data.roomdb.LocalDao
import com.architecture.data.roomdb.LocalDatabase
import com.architecture.data.sharedpref.SharedPref
import dagger.Module
import dagger.Provides
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import javax.inject.Singleton

val appModule = module {

    single<SharedPreferences> {
        return@single getPref(androidContext())
    }
}

fun getPref(context: Context) : SharedPreferences {
    return SharedPref.getPref(context)
}

/*
@Module(includes = [RetrofitModule::class])
class AppModule internal constructor(var context: MyApplication){

    @Provides
    @Singleton
    fun getContext() : Context {
        return context
    }

    @Provides
    @Singleton
    fun getLocalDao(localDatabase: LocalDatabase) : LocalDao {
        return localDatabase.localDao()
    }

    @Provides
    @Singleton
    fun getLocalDatabase(context: Context) : LocalDatabase {
        return Room
            .databaseBuilder(context, LocalDatabase::class.java, "local_database.db")
//            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun getPref(context: Context) : SharedPreferences {
        return SharedPref.getPref(context)
    }

}*/
