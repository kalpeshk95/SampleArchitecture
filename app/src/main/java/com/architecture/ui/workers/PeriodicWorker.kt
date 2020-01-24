package com.architecture.ui.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.architecture.utils.Constant
import com.architecture.utils.Log
import org.jetbrains.annotations.NotNull

class PeriodicWorker(@NotNull appContext: Context, @NotNull params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        Log.i(Constant.TAG, "Periodic Request Enqueue...!")
        return Result.success()
    }
}