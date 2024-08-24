package com.architecture.ui.fragments.menu

import androidx.lifecycle.MediatorLiveData
import com.architecture.data.remote.RemoteRepository
import com.architecture.network.Resource
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.utils.Constant
import com.architecture.utils.EmptyDataException
import com.architecture.wrapper.Employee
import timber.log.Timber

class MenuViewModel(private val remoteRepository: RemoteRepository) : BaseViewModel() {

    val listEmployee = MediatorLiveData<Resource<List<Employee>>>()

    init {
        fetchEmployees()
    }

    fun fetchEmployees() {

        val employeeSource = remoteRepository.fetchEmpData()

        listEmployee.addSource(employeeSource) { listResource ->

            if (listResource != null) {
                listEmployee.value = listResource
                if (listResource is Resource.Success) {
                    if (listResource.data != null) {
                        if (listResource.data.isEmpty()) {
                            listEmployee.value = Resource.Error(
                                EmptyDataException(Constant.QUERY_EXHAUSTED),
                                listResource.data
                            )
                        }
                    }
                    listEmployee.removeSource(employeeSource)
                } else if (listResource is Resource.Error) {
                    Timber.d("vm: banners: onChanged :: err :: msg: ${listResource.exception}")
                    listEmployee.removeSource(employeeSource)
                }

            } else {
                listEmployee.removeSource(employeeSource)
            }
        }
    }
}
