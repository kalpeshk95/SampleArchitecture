package com.architecture.data.source.remote

import com.architecture.data.wrapper.Employee
import com.architecture.domain.MyApplication
import io.reactivex.Observable
import javax.inject.Inject

class RemoteRepository @Inject constructor(): RemoteManager {

//    private val network = ApiClient.client!!.create(Network::class.java)

    @Inject
    lateinit var network :  Network

    init {
        MyApplication.component.inject(this)
    }

    override fun fetchEmpData(): Observable<List<Employee>> {
        return network.fetchEmployee()
    }
}