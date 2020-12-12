package com.architecture.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.architecture.wrapper.User

@Dao
interface LocalDao {

    @get:Query("SELECT * FROM user")
    val all: LiveData<List<User>>

    @Insert
    fun insertAll(users: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}