package com.architecture.core.logs

import android.os.Build
import android.util.Log
import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        // Workaround for devices that doesn't show lower priority logs
        var newPriority = priority

        if (Build.MANUFACTURER == "HUAWEI" || Build.MANUFACTURER == "samsung") {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) newPriority =
                Log.ERROR
        }

        super.log(newPriority, tag, message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        // Add log statements line number to the log
        return "${super.createStackElementTag(element)} # ${element.lineNumber} "
    }
}