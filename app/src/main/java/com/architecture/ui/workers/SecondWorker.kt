package com.architecture.ui.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.architecture.utils.Constant
import timber.log.Timber

class SecondWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY = "secondNumber"
    }

    override suspend fun doWork(): Result {

        Timber.i(Constant.TAG, "In SecondWorker")
        val number = inputData.getInt(KEY, 0)
        val data = Data.Builder().putInt(KEY, number).build()
        return Result.success(data)
    }
}