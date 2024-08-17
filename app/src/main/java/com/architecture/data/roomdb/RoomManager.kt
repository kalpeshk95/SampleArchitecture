package com.architecture.data.roomdb

import androidx.lifecycle.LiveData
import com.architecture.wrapper.User

interface RoomManager {
    fun getAll(): LiveData<List<User>>
    suspend fun insert(user: User): Result<User>
    suspend fun update(user: User): Result<User>
    suspend fun delete(user: User): Result<User>
    suspend fun deleteAll(): Result<String>
}