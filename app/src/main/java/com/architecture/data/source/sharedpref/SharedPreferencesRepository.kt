package com.architecture.data.source.sharedpref

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesRepository @Inject
constructor(/*context: Context*/) : SharedPrefManager {

//    private var sharedPreferences: SharedPreferences = SharedPref.getPref(context)

    @Inject
    lateinit var  sharedPreferences: SharedPreferences

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
