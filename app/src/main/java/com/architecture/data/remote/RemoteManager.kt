package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.network.Resource
import com.architecture.wrapper.Employee

interface RemoteManager {

    fun fetchEmpData(): LiveData<Resource<List<Employee>>>
}