package com.architecture.ui.fragments.room

interface AdapterClickListener {
    fun onViewClick(position: Int)
    fun onEditClick(position: Int)
    fun onDeleteClick(position: Int)
}