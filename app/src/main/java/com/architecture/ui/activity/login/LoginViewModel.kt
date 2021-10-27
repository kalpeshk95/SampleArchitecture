package com.architecture.ui.activity.login

import androidx.lifecycle.MutableLiveData
import com.architecture.data.sharedpref.Prefs
import com.architecture.ui.fragments.base.BaseViewModel

class LoginViewModel(private val prefs: Prefs) : BaseViewModel() {

//    var username = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")
//    var password = ObservableField("")//(if (BuildConfig.DEBUG) "root" else "")

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    var login = MutableLiveData<Boolean>()

    fun onLoginClicked(username: String, password: String) {

        when {
            username.isEmpty() /*|| username.value != "root"*/ -> {
                toastMsg.value = "Please enter appropriate UserName"
                return
            }
            password.isEmpty() /*|| password.value != "root"*/ -> {
                toastMsg.value = "Please enter appropriate Password"
                return
            }
            username != password -> {
                toastMsg.value = "UserName and password does not match"
                return
            }
            else -> {
                prefs.setUserName(username)
                prefs.setPassword(password)

                login.value = true
            }
        }
    }

    fun onForgetPassWd() {
        toastMsg.value = "UserName and password must be same...!"
    }

    fun getUserName() = prefs.getUserName()
    fun getPassword() = prefs.getPassword()

}


