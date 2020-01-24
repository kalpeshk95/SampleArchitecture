package com.architecture.ui.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.architecture.utils.Constant
import com.architecture.utils.Log
import org.jetbrains.annotations.NotNull

class ThirdWorker(@NotNull context: Context, @NotNull workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY = "thirdNumber"
    }

    override suspend fun doWork(): Result {

        Log.i(Constant.TAG, "In ThirdWorker")
        val number1 = inputData.getInt(FirstWorker.KEY, 0)
        Log.i(Constant.TAG, "First Number : $number1")

        val number2 = inputData.getInt(SecondWorker.KEY, 0)
        Log.i(Constant.TAG, "Second Number : $number2")

        val data = Data.Builder().putInt(KEY, number1 + number2).build()
        Log.i(Constant.TAG, "Addition : " + data.getInt(KEY,0))

        return Result.success(data)
    }
}