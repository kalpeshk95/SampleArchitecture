package com.architecture.ui.fragments.menu

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.architecture.data.source.remote.RemoteRepository
import com.architecture.data.wrapper.Employee
import com.architecture.domain.MyApplication
import com.architecture.ui.fragments.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class MenuViewModel(@NotNull appContext: Application) : BaseViewModel(appContext) {

    @Inject
    lateinit var remoteRepository: RemoteRepository

    private lateinit var disposable: Disposable

    var showLoader = MutableLiveData<Boolean>()
    var listEmployee = MutableLiveData<List<Employee>>()

    init {
        MyApplication.component.inject(this)
    }

    fun listEmployee() {
        showLoader.value = true

        disposable = remoteRepository.fetchEmpData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listEmployee.value = it
                showLoader.value = false
            },{
                showLoader.value = false
                toastMsg.value = it.message
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
