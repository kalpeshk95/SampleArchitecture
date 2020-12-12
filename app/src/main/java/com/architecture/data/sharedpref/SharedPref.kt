package com.architecture.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.architecture.utils.Constant
import com.architecture.utils.Log

object SharedPref {

    @Volatile
    private var sharedPreferences: SharedPreferences? = null

    @Synchronized
    fun getPref(context: Context): SharedPreferences {

        Log.i(Constant.TAG,"SharedPref")

        if (sharedPreferences == null) {
            val prefName = "DEMOA"
            sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        }
        return sharedPreferences!!
    }
}