package com.architecture.ui.fragments.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var toastMsg = MutableLiveData<String>()

}
