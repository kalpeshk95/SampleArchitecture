package com.architecture.domain

import com.architecture.data.remote.RemoteRepository
import com.architecture.ui.activity.login.LoginViewModel
import com.architecture.ui.fragments.menu.MenuViewModel
import com.architecture.ui.fragments.room.RoomViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface MyComponent {

    fun inject(model: LoginViewModel)
    fun inject(model: MenuViewModel)
    fun inject(model: RoomViewModel)

    //New code for retrofit injection
    fun inject(repository: RemoteRepository)
}