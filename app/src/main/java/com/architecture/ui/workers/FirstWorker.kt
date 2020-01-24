package com.architecture.ui.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.architecture.utils.Constant
import com.architecture.utils.Log
import org.jetbrains.annotations.NotNull

class FirstWorker(@NotNull context: Context, @NotNull workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY = "firstNumber"
    }

    override suspend fun doWork(): Result {

        Log.i(Constant.TAG, "In FirstWorker")
        val number = inputData.getInt(KEY, 0)
        val data = Data.Builder().putInt(KEY, number).build()
        return Result.success(data)
    }
}