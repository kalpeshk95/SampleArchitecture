package com.architecture.data.sharedpref

interface SharedPrefManager {
    fun putUserName(user: String)
    fun getUserName(): String
    fun putPassword(passwd: String)
    fun getPasswd(): String
}