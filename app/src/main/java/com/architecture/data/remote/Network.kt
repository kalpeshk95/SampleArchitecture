package com.architecture.data.remote

import androidx.lifecycle.LiveData
import com.architecture.network.ApiResponse
import com.architecture.wrapper.Employee
import retrofit2.http.GET

interface Network {

    @GET("548c010d-1dfa-4530-bcbf-1db47828de61?mocky-delay=2s")
    fun fetchEmployee(): LiveData<ApiResponse<List<Employee>>>
}