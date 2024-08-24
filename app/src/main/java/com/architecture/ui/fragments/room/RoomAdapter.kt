package com.architecture.ui.fragments.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.databinding.ListRoomUsersBinding
import com.architecture.wrapper.User

class RoomAdapter(private val listener: AdapterClickListener) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private var userList = listOf<User>() // Use immutable list

    class ViewHolder(val binding: ListRoomUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListRoomUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setItems(listItems: List<User>) {
        userList = listItems // Directly assign the new list
        notifyDataSetChanged()
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userList[position]
        with(holder.binding) {
            tvName.text = item.name
            root.setOnClickListener { listener.onViewClick(item) }
            ivEdit.setOnClickListener { listener.onEditClick(item) }
            ivDelete.setOnClickListener { listener.onDeleteClick(item) }
        }
    }
}