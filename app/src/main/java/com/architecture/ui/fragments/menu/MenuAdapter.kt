package com.architecture.ui.fragments.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.architecture.R
import com.architecture.data.wrapper.Employee
import com.architecture.databinding.ListDashboardBinding

class MenuAdapter(
    private val context: Context,
    private val list: List<Employee>
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ListDashboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ListDashboardBinding>(
            LayoutInflater.from(context),
            R.layout.list_dashboard,
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