package com.architecture.ui.fragments.room

import com.architecture.data.wrapper.User

interface AdapterClickListener {
    fun onViewClick(user: User)
    fun onEditClick(user: User)
    fun onDeleteClick(user: User)
}