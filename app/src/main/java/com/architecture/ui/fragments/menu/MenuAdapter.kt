package com.architecture.ui.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architecture.databinding.ListDashboardBinding
import com.architecture.wrapper.Employee

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var employeeList = listOf<Employee>()

    class ViewHolder(val binding: ListDashboardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setItems(listItems: List<Employee>) {
        employeeList = listItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = employeeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employeeList[position]
        with(holder.binding) {
            tvId.text = item.id
            tvName.text = item.name
            tvCompany.text = item.company
            tvMob.text = item.number
        }
    }
}