package com.architecture.ui.fragments.menu

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.app.MyApplication
import com.architecture.data.remote.RemoteRepository
import com.architecture.network.Resource
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.utils.Constant
import com.architecture.utils.EmptyDataException
import com.architecture.wrapper.Employee
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import javax.inject.Inject

class MenuViewModel(@NotNull appContext: Application) : BaseViewModel(appContext) {

    @Inject
    lateinit var remoteRepository: RemoteRepository

    var showLoader = MutableLiveData<Boolean>()
//    var listEmployee = MutableLiveData<List<Employee>>()

    val listEmployee = MediatorLiveData<Resource<List<Employee>>>()

    init {
        MyApplication.component.inject(this)
    }

    fun listEmployee() {
        showLoader.value = true

        val employeeSource = remoteRepository.fetchEmpData()

        listEmployee.addSource(employeeSource) { listResource ->

            if (listResource != null) {
                listEmployee.value = listResource
                if (listResource is Resource.Success) {
                    if (listResource.data != null) {
                        if (listResource.data.isEmpty()) {
                            Timber.d("vm: banners: onChanged :: listResource size: ${listResource.data.size} and query is exhausted")
                            listEmployee.value = Resource.Error(
                                EmptyDataException(Constant.QUERY_EXHAUSTED),
                                listResource.data
                            )
                        }
                    }
                    listEmployee.removeSource(employeeSource)
                } else if (listResource is Resource.Error) {
                    Timber.d("vm: banners: onChanged :: err :: msg: ${listResource.exception}")
                    listEmployee.removeSource(employeeSource)
                }

            } else {
                listEmployee.removeSource(employeeSource)
            }
        }

//        disposable = remoteRepository.fetchEmpData()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.i("List size : ${it.size}")
//                showLoader.value = false
//                listEmployee.value = it
//            },{
//                showLoader.value = false
//                toastMsg.value = it.message
//            })
    }

    override fun onCleared() {
        super.onCleared()
//        disposable.dispose()
    }
}
