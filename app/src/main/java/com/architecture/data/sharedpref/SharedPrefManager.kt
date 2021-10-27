package com.architecture.data.sharedpref

interface SharedPrefManager {
    fun setUserName(user: String)
    fun getUserName(): String
    fun setPassword(passwd: String)
    fun getPassword(): String
}