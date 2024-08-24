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

    enum class Action {
        ADD,
        UPDATE,
        DELETE,
        DELETE_ALL
    }

    val name = MutableLiveData("")
    val age = MutableLiveData("")
    val salary = MutableLiveData("")

    private val user = User()
    val usersList: LiveData<List<User>> = roomRepository.getAll() // Directly assign LiveData
    val flagDialog = MutableLiveData<Boolean>()

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = _showLoader

    fun addUpdateUser(isAdding: Boolean) {
        val userName = name.value.orEmpty()
        val userAge = age.value.orEmpty()
        val userSalary = salary.value.orEmpty()

        if (userName.isBlank() || userAge.isBlank() || userSalary.isBlank()) {
            toastMsg.value = "Please fill in all fields"
            return
        }

        user.name = userName
        user.age = userAge.toIntOrNull() ?: 0
        user.salary = userSalary.toIntOrNull() ?: 0

        viewModelScope.launch {
            _showLoader.value = true
            val result = if (isAdding) roomRepository.insert(user) else roomRepository.update(user)
            handleResult(result, if (isAdding) Action.ADD else Action.UPDATE)
            _showLoader.value = false
        }
    }

    fun delete(user: User) = viewModelScope.launch {
        _showLoader.value = true
        handleResult(roomRepository.delete(user), Action.DELETE)
        _showLoader.value = false
    }

    fun deleteAll() = viewModelScope.launch {
        _showLoader.value = true
        handleResult(roomRepository.deleteAll(), Action.DELETE_ALL)
        _showLoader.value = false
    }

    private fun handleResult(result: Result<Any>, action: Action) {
        when (result) {
            is Result.Success -> {
                if (result.data is String) {
                    toastMsg.value = result.data
                } else {
                    val user = result.data as User
                    toastMsg.value = when (action) {
                        Action.ADD -> "User ${user.name} added successfully"
                        Action.UPDATE -> "User ${user.name} updated successfully"
                        Action.DELETE -> "User ${user.name} deleted successfully"
                        else -> ""
                    }
                }
                flagDialog.value = true
            }

            is Result.Error -> {
                toastMsg.value = when (action) {
                    Action.ADD -> "Error adding user: ${result.exception.message}"
                    Action.UPDATE -> "Error updating user: ${result.exception.message}"
                    Action.DELETE -> "Error deleting user: ${result.exception.message}"
                    Action.DELETE_ALL -> "Error deleting all user: ${result.exception.message}"
                }
            }
            // No need to handle Result.Loading here as we're showing the loader externally
            Result.Loading -> TODO()
        }
    }
}