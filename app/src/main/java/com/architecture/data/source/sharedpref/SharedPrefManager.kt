package com.architecture.data.source.sharedpref

interface SharedPrefManager {
    fun putUserName(user: String)
    fun getUserName(): String
    fun putPassword(passwd: String)
    fun getPasswd(): String
}