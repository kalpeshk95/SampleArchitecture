package com.architecture.ui.activity.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.architecture.app.MyApplication
import com.architecture.data.sharedpref.SharedPreferencesRepository
import com.architecture.ui.fragments.base.BaseViewModel
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class LoginViewModel(@NotNull appContext: Application) : BaseViewModel(appContext) {

    @Inject
    lateinit var sharedPrefRepository: SharedPreferencesRepository

//    var username = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")
//    var password = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    var login = MutableLiveData<Boolean>()

    init {
        MyApplication.component.inject(this)
    }

    fun onLoginClicked(username: String, password: String) {

        if (username.isEmpty() /*|| username.value != "root"*/) {
            toastMsg.value = "Please enter appropriate UserName"
            return
        } else if (password.isEmpty() /*|| password.value != "root"*/) {
            toastMsg.value = "Please enter appropriate Password"
            return
        } else if (username != password) {
            toastMsg.value = "UserName and password does not match"
            return
        }
        sharedPrefRepository.putUserName(username)
        sharedPrefRepository.putPassword(password)

        login.value = true
    }

    fun onForgetPassWd() {
        toastMsg.value = "UserName and password must be same...!"
    }

    fun getUserName() = sharedPrefRepository.getUserName()
    fun getPassword() = sharedPrefRepository.getPasswd()

}


