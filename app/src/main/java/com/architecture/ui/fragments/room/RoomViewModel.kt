package com.architecture.ui.fragments.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.architecture.data.roomdb.Result
import com.architecture.data.roomdb.RoomRepository
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.wrapper.User
import kotlinx.coroutines.launch

class RoomViewModel(private val roomRepository: RoomRepository) : BaseViewModel() {

    var name = MutableLiveData("")
    var age = MutableLiveData("")
    var salary = MutableLiveData("")

    val user = User()
    var usersList: LiveData<List<User>>? = null
    var flagDialog = MutableLiveData<Boolean>()

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = _showLoader

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
                    when (val result = roomRepository.insert(user)) {
                        is Result.Loading -> {
                            _showLoader.value = true
                        }
                        is Result.Success -> {
                            _showLoader.value = false
                            toastMsg.value = "User ${result.data.name} added successfully"
                            flagDialog.value = true
                        }
                        is Result.Error -> {
                            _showLoader.value = false
                            toastMsg.value = "Error adding user: ${result.exception.message}"
                        }
                    }
                }
            } else {
                viewModelScope.launch {
                    when (val result = roomRepository.update(user)) {
                        is Result.Loading -> {
                            _showLoader.value = true
                        }
                        is Result.Success -> {
                            _showLoader.value = false
                            toastMsg.value = "User ${result.data.name} updated successfully"
                            flagDialog.value = true
                        }
                        is Result.Error -> {
                            _showLoader.value = false
                            toastMsg.value = "Error updating user: ${result.exception.message}"
                        }
                    }
                }
            }
        }
    }

    fun delete(user: User) = viewModelScope.launch {
        when (val result = roomRepository.delete(user)) {
            is Result.Loading -> {
                _showLoader.value = true
            }
            is Result.Success -> {
                _showLoader.value = false
                toastMsg.value = "User ${result.data.name} deleted successfully"
            }
            is Result.Error -> {
                _showLoader.value = false
                toastMsg.value = "Error deleting user: ${result.exception.message}"
            }
        }
    }

    fun deleteAll() = viewModelScope.launch {
        when (val result = roomRepository.deleteAll()) {
            is Result.Loading -> {
                _showLoader.value = true
            }
            is Result.Success -> {
                _showLoader.value = false
                toastMsg.value = result.data
            }
            is Result.Error -> {
                _showLoader.value = false
                toastMsg.value = "Error deleting records: ${result.exception.message}"
            }
        }
    }
}
