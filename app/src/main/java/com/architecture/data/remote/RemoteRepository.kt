package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.app.AppExecutors
import com.architecture.network.ApiResponse
import com.architecture.network.ApiSuccessResponse
import com.architecture.network.NetworkBoundResource
import com.architecture.network.Resource
import com.architecture.utils.AbsentLiveData
import com.architecture.wrapper.Employee

class RemoteRepository(private val appExecutors: AppExecutors, private val network: Network) :
    RemoteManager {

//    private val appExecutors: AppExecutors by inject(AppExecutors::class.java)
//    private val network: Network by inject(Network::class.java)

    override fun fetchEmpData(): LiveData<Resource<List<Employee>>> {
        return object : NetworkBoundResource<List<Employee>, List<Employee>>(appExecutors) {

            private var employee: List<Employee>? = null

            override fun saveCallResult(item: List<Employee>) {
                employee = item
            }

            override fun shouldFetch(data: List<Employee>?) = true

            override fun loadFromDb(): LiveData<List<Employee>> {
                return if (employee == null) {
                    AbsentLiveData.create()
                } else {
                    object : LiveData<List<Employee>>() {
                        override fun onActive() {
                            super.onActive()
                            value = employee
                        }
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<List<Employee>>> {
                return network.fetchEmployee()
            }

            override fun processResponse(response: ApiSuccessResponse<List<Employee>>): List<Employee> {
                return response.body
            }

        }.asLiveData()
    }
}