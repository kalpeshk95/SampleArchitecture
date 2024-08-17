package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.network.ApiResponse
import com.architecture.wrapper.Employee
import retrofit2.http.GET

interface Network {

    @GET("5acedacb-4f26-44a6-a8ea-c1d3cccbbe6d?mocky-delay=2s")
    fun fetchEmployee(): LiveData<ApiResponse<List<Employee>>>
}