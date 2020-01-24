package com.architecture.data.source.remote

import com.architecture.data.wrapper.Employee
import io.reactivex.Observable
import javax.inject.Inject

class RemoteRepository @Inject constructor(): RemoteManager {

    private val network = ApiClient.client!!.create(Network::class.java)

    override fun fetchEmpData(): Observable<List<Employee>> {
        return network.fetchEmployee()
    }
}