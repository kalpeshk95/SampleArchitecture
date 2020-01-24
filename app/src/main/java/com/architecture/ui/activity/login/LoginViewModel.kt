package com.architecture.ui.activity.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.data.source.sharedpref.SharedPreferencesRepository
import com.architecture.domain.MyApplication
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class LoginViewModel(@NotNull appContext: Application) : BaseViewModel(appContext) {

//    private val uiScope = CoroutineScope(Dispatchers.Main)
//    private val bgScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var sharedPrefRepository: SharedPreferencesRepository

//    var username = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")
//    var password = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    var login = MutableLiveData<Boolean>()
//    var liveDataMerger = MediatorLiveData<Boolean>()

    init {
        MyApplication.component.inject(this)
//        liveDataMerger.addSource(login, Observer {
//            liveDataMerger.value = login.value
//        })
    }

    fun setPrefValue() {
//        username.set(sharedPrefRepository.getUserName())
//        password.set(sharedPrefRepository.getPasswd())

        username.value = sharedPrefRepository.getUserName()
        password.value = sharedPrefRepository.getPasswd()
    }

    fun onLoginClicked() {

        if (username.value?.length == 0 /*|| username.value != "root"*/) {
            toastMsg.value = "Please enter appropriate UserName"
            return
        } else if (password.value?.length == 0 /*|| password.value != "root"*/) {
            toastMsg.value = "Please enter appropriate Password"
            return
        } else if (!(username.value.equals(password.value))) {
            toastMsg.value = "UserName and password does not match"
            return
        }

        sharedPrefRepository.putUserName(username.value!!)
        sharedPrefRepository.putPassword(password.value!!)

        login.value = true
    }

    fun onForgetPassWd() {
        toastMsg.value = "UserName and password must be same...!"
    }

}


