package com.architecture.ui.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.data.wrapper.Employee
import com.architecture.databinding.ListDashboardBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val employeeList: MutableList<Employee> = mutableListOf()

    class ViewHolder(var binding: ListDashboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDashboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    fun setItems(listItems: List<Employee>) {
        employeeList.clear()
        employeeList.addAll(listItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = employeeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employeeList[position]
        holder.binding.tvId.text = item.id.toString()
        holder.binding.tvName.text = item.name
        holder.binding.tvCompany.text = item.company
        holder.binding.tvMob.text = item.number

    }
}