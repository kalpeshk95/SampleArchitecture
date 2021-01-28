package com.architecture.data.sharedpref

import android.content.SharedPreferences

class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences) :
    SharedPrefManager {

    private val userName = "username"
    private val passWord = "password"

    override fun putUserName(user: String) {
        sharedPreferences.edit().putString(userName, user).apply()
    }

    override fun putPassword(passwd: String) {
        sharedPreferences.edit().putString(passWord, passwd).apply()
    }

    override fun getUserName(): String {
        return if (sharedPreferences.contains(userName)) getStringPref(userName) else ""
    }

    override fun getPasswd(): String {
        return if (sharedPreferences.contains(passWord)) getStringPref(passWord) else ""
    }

    private fun getStringPref(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }
}
