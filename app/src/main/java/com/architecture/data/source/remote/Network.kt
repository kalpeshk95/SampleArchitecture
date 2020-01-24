package com.architecture.data.source.remote

import com.architecture.data.wrapper.Employee
import io.reactivex.Observable
import retrofit2.http.GET

interface Network {

    @GET("5e1d70b73600002c00c74023?mocky-delay=5s")
    fun fetchEmployee() : Observable<List<Employee>>
}