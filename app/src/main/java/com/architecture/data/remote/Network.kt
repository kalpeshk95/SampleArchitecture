package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.network.ApiResponse
import com.architecture.wrapper.Employee
import retrofit2.http.GET

interface Network {

    @GET("5e1d70b73600002c00c74023?mocky-delay=5s")
    fun fetchEmployee(): LiveData<ApiResponse<List<Employee>>>
}