package com.architecture.data.roomdb

import androidx.lifecycle.LiveData
import com.architecture.wrapper.User

class RoomRepository(private val dao: LocalDao) : RoomManager {

    override fun getAll(): LiveData<List<User>> {
        return dao.all
    }

    override suspend fun insert(user: User): Result<User> {
        return try {
            dao.insertAll(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun update(user: User): Result<User> {
        return try {
            dao.update(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun delete(user: User): Result<User> {
        return try {
            dao.delete(user)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteAll(): Result<String> {
        return try {
            dao.deleteAll()
            Result.Success("All records deleted successfully")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}