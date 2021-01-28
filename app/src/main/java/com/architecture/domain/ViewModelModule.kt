package com.architecture.domain

import com.architecture.ui.activity.login.LoginViewModel
import com.architecture.ui.fragments.base.BaseViewModel
import com.architecture.ui.fragments.menu.MenuViewModel
import com.architecture.ui.fragments.room.RoomViewModel
import com.architecture.ui.fragments.workmanager.WorkViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {

    viewModel { BaseViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { MenuViewModel(get()) }
    viewModel { RoomViewModel(get()) }
    viewModel { WorkViewModel(get()) }
}
