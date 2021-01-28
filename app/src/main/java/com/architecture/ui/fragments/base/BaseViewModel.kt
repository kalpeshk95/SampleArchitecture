package com.architecture.ui.fragments.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    var toastMsg = MutableLiveData<String>()

    lateinit var disposable: Disposable

//    var myJob: Job? = null
//
//    override fun onCleared() {
//        super.onCleared()
//        myJob?.cancel()
//    }

}
