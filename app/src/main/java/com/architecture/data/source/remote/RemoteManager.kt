package com.architecture.data.source.remote

import com.architecture.data.wrapper.Employee
import io.reactivex.Observable

interface RemoteManager {

    fun fetchEmpData(): Observable<List<Employee>>
}