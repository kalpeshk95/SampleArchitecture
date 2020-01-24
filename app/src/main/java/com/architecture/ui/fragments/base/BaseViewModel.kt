package com.architecture.ui.fragments.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job

    open class BaseViewModel(appContext: Application): AndroidViewModel(appContext){

    var toastMsg = MutableLiveData<String>()

    var myJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        myJob?.cancel()
    }

}
