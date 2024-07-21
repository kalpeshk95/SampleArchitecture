package com.architecture.ui.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.architecture.utils.Constant
import timber.log.Timber

class PeriodicWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        Timber.i(Constant.TAG, "Periodic Request Enqueue...!")
        return Result.success()
    }
}