package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.network.ApiResponse
import com.architecture.wrapper.Employee
import retrofit2.http.GET

interface Network {

    @GET("sample/employee")
    fun fetchEmployee(): LiveData<ApiResponse<List<Employee>>>
}