package com.architecture.ui.fragments.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.architecture.R
import com.architecture.data.wrapper.User
import com.architecture.databinding.ListRoomUsersBinding

class RoomAdapter(
    private val context: Context,
    private val listener: AdapterClickListener,
    private val list: List<User>
) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ListRoomUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onViewClick(adapterPosition)
            }

            binding.ivEdit.setOnClickListener {
                listener.onEditClick(adapterPosition)
            }

            binding.ivDelete.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListRoomUsersBinding>(
            LayoutInflater.from(context),
            R.layout.list_room_users,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.model = item
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size
}