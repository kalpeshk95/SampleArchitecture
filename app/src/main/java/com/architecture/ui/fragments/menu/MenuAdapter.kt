package com.architecture.ui.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architecture.databinding.ListDashboardBinding
import com.architecture.wrapper.Employee

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var employeeList = emptyList<Employee>()

    class ViewHolder(val binding: ListDashboardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setItems(newList: List<Employee>) {
        val diffResult = DiffUtil.calculateDiff(EmployeeDiffCallback(employeeList, newList))
        employeeList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = employeeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employeeList[position]
        with(holder.binding) {
            tvId.text = item.id.toString()
            tvName.text = getFirstTwoWords(item.name.toString())
            tvCompany.text = item.company
            tvMob.text = item.phone
        }
    }

    private fun getFirstTwoWords(paragraph: String): String {
        val words = paragraph.split(" ") // Split by space
        if (words.size < 2) {
            return paragraph // Return the whole paragraph if it has less than 2 words
        }
        return words.take(2).joinToString(" ") // Take first 2 and join with space
    }

    class EmployeeDiffCallback(
        private val oldList: List<Employee>,
        private val newList: List<Employee>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}