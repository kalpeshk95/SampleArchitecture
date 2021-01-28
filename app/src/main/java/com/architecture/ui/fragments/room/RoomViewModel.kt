package com.architecture.ui.fragments.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.architecture.data.roomdb.RoomManager
import com.architecture.data.roomdb.RoomRepository
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.wrapper.User
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import org.koin.java.KoinJavaComponent.inject

class RoomViewModel(@NotNull appContext: Application) : BaseViewModel(appContext) {

    private val roomRepository: RoomRepository by inject(
        RoomRepository::class.java
    )

    var name = MutableLiveData("")
    var age = MutableLiveData("")
    var salary = MutableLiveData("")

    val user = User()
    var usersList: LiveData<List<User>>? = null
    var flagDialog = MutableLiveData<Boolean>()

    init {
        usersList = roomRepository.getAll()
    }

    fun addUpdateUser(flag: Boolean) {

        if (name.value?.length == 0) {
            toastMsg.value = "First name is blank"
            return
        } else if (age.value?.length == 0) {
            toastMsg.value = "Age is blank"
            return
        } else if (salary.value?.length == 0) {
            toastMsg.value = "Salary is blank"
            return
        } else {

            user.name = name.value!!
            user.age = age.value!!.toInt()
            user.salary = salary.value!!.toInt()

            if (flag) {
                viewModelScope.launch {
                    roomRepository.insert(object : RoomManager.CallbackManager {
                        override fun onSetMessage(msg: String) {
                            if (msg.contains("UNIQUE constraint failed")) toastMsg.value =
                                "Record already found..."
                            else {
                                toastMsg.value = msg
                                flagDialog.value = true
                            }
                        }
                    }, user)
                }
            } else {
                viewModelScope.launch {
                    roomRepository.update(object : RoomManager.CallbackManager {
                        override fun onSetMessage(msg: String) {
                            toastMsg.value = msg
                            flagDialog.value = true
                        }
                    }, user)
                }
            }
        }
    }

    fun delete(user: User) = viewModelScope.launch {
        roomRepository.delete(object : RoomManager.CallbackManager {
            override fun onSetMessage(msg: String) {
                toastMsg.value = msg
            }
        }, user)
    }

    fun deleteAll() = viewModelScope.launch {
        roomRepository.deleteAll(object : RoomManager.CallbackManager {
            override fun onSetMessage(msg: String) {
                toastMsg.value = msg
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        roomRepository.disposable?.dispose()
    }
}
