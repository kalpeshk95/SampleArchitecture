package com.architecture.ui.fragments.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.databinding.ListRoomUsersBinding
import com.architecture.wrapper.User

class RoomAdapter(
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    private val userList: MutableList<User> = mutableListOf()

    class ViewHolder(var binding: ListRoomUsersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRoomUsersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setItems(listItems: List<User>) {
        userList.clear()
        userList.addAll(listItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userList[position]
        holder.binding.tvName.text = item.name
        holder.binding.root.setOnClickListener {
            listener.onViewClick(item)
        }

        holder.binding.ivEdit.setOnClickListener {
            listener.onEditClick(item)
        }

        holder.binding.ivDelete.setOnClickListener {
            listener.onDeleteClick(item)
        }
    }
}