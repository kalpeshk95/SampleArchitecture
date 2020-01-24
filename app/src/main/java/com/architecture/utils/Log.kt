package com.architecture.utils

import android.util.Log
import com.architecture.BuildConfig

object Log {

    fun i(text: String) {
        if (BuildConfig.DEBUG) Log.i(Constant.TAG, text)
    }

    fun i(tag: String, text: String) {
        if (BuildConfig.DEBUG) Log.i(tag, text)
    }

    fun v(text: String) {
        if (BuildConfig.DEBUG) Log.v(Constant.TAG, text)
    }

    fun v(tag: String, text: String) {
        if (BuildConfig.DEBUG) Log.v(tag, text)
    }

    fun d(text: String) {
        if (BuildConfig.DEBUG) Log.d(Constant.TAG, text)
    }

    fun d(tag: String, text: String) {
        if (BuildConfig.DEBUG) Log.d(tag, text)
    }

    fun w(text: String) {
        if (BuildConfig.DEBUG) Log.w(Constant.TAG, text)
    }

    fun w(tag: String, text: String) {
        if (BuildConfig.DEBUG) Log.w(tag, text)
    }

    fun e(text: String) {
        if (BuildConfig.DEBUG) Log.e(Constant.TAG, text)
    }

    fun e(tag: String, text: String) {
        if (BuildConfig.DEBUG) Log.e(tag, text)
    }

    fun e(text: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) Log.e(Constant.TAG, text, throwable)
    }

    fun e(tag: String, text: String, throwable: Throwable) {
        if (BuildConfig.DEBUG) Log.e(tag, text, throwable)
    }
}