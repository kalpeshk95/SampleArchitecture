package com.architecture.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.data.sharedpref.Prefs
import com.architecture.ui.fragments.base.BaseViewModel

class LoginViewModel(private val prefs: Prefs) : BaseViewModel() {

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login
    fun onLoginClicked(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            toastMsg.value = "Please enter both username and password"
            return
        }

        if (username != password) {
            toastMsg.value = "Username and password do not match"
            return
        }

        prefs.setUserName(username)
        prefs.setPassword(password)
        _login.value = true
    }

    fun setLogin() {
        _login.value = false
    }

    fun onForgetPassWd() {
        toastMsg.value = "Username and password must be the same...!"
    }

    fun getUserName() = prefs.getUserName()
    fun getPassword() = prefs.getPassword()
}