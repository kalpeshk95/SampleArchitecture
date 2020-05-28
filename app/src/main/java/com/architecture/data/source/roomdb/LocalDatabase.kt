package com.architecture.data.source.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.architecture.data.wrapper.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localDao(): LocalDao

//    companion object {
//
//        @Volatile
//        private var roomDatabase: LocalDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): LocalDatabase? {
//            if (roomDatabase == null) {
//                roomDatabase = Room.databaseBuilder(context, LocalDatabase::class.java, "local_database").build()
//            }
//            return roomDatabase
//        }
//    }
}