package com.architecture.core.logs

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {

    companion object {
        private const val MAX_LOG_LENGTH = 4000
        //private const val UNKNOWN_TAG = "UNKNOWN_TAG"
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean =
        !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (!isLoggable(tag, priority)) {
            return
        }

        if (priority == Log.ERROR && t != null) {
            //TODO Open this after configuring crashlytics
            //Crashlytics.logException(t)
        }

        if (message.length < MAX_LOG_LENGTH) {
            //logToLogcat(priority, tag, message)
            logToCrashlyics(priority, tag, message)
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                //logToLogcat(priority, tag, part)
                logToCrashlyics(priority, tag, part)
                i = end
            } while (i < newline)
            i++
        }
    }

    private fun logToCrashlyics(priority: Int, tag: String?, message: String) {
        if (priority == Log.ERROR) {
            //TODO Open this after configuring crashlytics
            //Crashlytics.log(priority, tag ?: UNKNOWN_TAG, message)
        }
    }
}