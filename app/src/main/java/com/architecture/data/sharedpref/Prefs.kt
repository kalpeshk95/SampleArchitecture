package com.architecture.data.sharedpref

import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(private val prefs: SharedPreferences) : SharedPrefManager {

    companion object {
        private const val PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME"
        private const val PREF_KEY_USER_PASSWORD = "PREF_KEY_USER_PASSWORD"
    }

    override fun setUserName(user: String) {
        prefs.edit { putString(PREF_KEY_USER_NAME, user) }
    }

    override fun getUserName(): String = prefs.getString(PREF_KEY_USER_NAME, "") ?: ""

    override fun setPassword(passwd: String) {
        prefs.edit { putString(PREF_KEY_USER_PASSWORD, passwd) }
    }

    override fun getPassword(): String = prefs.getString(PREF_KEY_USER_PASSWORD, "") ?: ""

}