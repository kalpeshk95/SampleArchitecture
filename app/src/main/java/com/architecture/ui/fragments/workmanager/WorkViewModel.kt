package com.architecture.ui.fragments.workmanager

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.ui.workers.FirstWorker
import com.architecture.ui.workers.PeriodicWorker
import com.architecture.ui.workers.SecondWorker
import com.architecture.ui.workers.ThirdWorker
import java.util.*
import java.util.concurrent.TimeUnit

class WorkViewModel(appContext: Application) : BaseViewModel(appContext) {

    val firstNumber = MutableLiveData("")
    val secondNumber = MutableLiveData("")
    var result : LiveData<WorkInfo>? = null
    var list = LinkedList<OneTimeWorkRequest>()

    private val workManager: WorkManager = WorkManager.getInstance(appContext)
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    private val oneTimeWorkRequest3 = OneTimeWorkRequest.Builder(ThirdWorker::class.java)
        .setConstraints(constraints)
        .build()

    init {
        result = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest3.id)
    }

    fun addRequest() {
        val oneTimeWorkRequest1 = OneTimeWorkRequest.Builder(FirstWorker::class.java)
            .setConstraints(constraints)
            .setInputData(createInputDataForUri(FirstWorker.KEY,Integer.parseInt(firstNumber.value!!)))
            .build()

        val oneTimeWorkRequest2 = OneTimeWorkRequest.Builder(SecondWorker::class.java)
            .setConstraints(constraints)
            .setInputData(createInputDataForUri(SecondWorker.KEY, Integer.parseInt(secondNumber.value!!)))
            .build()

        list.add(oneTimeWorkRequest1)
        list.add(oneTimeWorkRequest2)

        workManager.beginWith(list).then(oneTimeWorkRequest3).enqueue()
    }

    fun addPeriodicRequest(){
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(PeriodicWorker::class.java,15, TimeUnit.MINUTES)
            .addTag("Periodic")
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork("periodicRequest", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest)
    }

    private fun createInputDataForUri(key: String, number: Int): Data {
        val data = Data.Builder()
        data.putInt(key, number)
        return data.build()
    }
}
