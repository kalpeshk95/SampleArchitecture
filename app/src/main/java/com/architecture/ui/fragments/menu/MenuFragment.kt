package com.architecture.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.architecture.R
import com.architecture.databinding.FragmentMenuBinding
import com.architecture.ui.fragments.base.BaseFragment

class MenuFragment : BaseFragment() {

    private val model by lazy { ViewModelProvider(this).get(MenuViewModel::class.java) }
    private lateinit var binding: FragmentMenuBinding
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = setFragmentLayout(R.layout.fragment_menu, container)
        binding.model = model

//        return inflater.inflate(R.layout.fragment_menu, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {
        createAdapter()
        if (model.listEmployee.value == null) model.listEmployee()

        model.listEmployee.observe(this) {
            adapter = MenuAdapter(activity(), it)
            binding.myAdapter = adapter
            if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
        }

        model.showLoader.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        model.toastMsg.observe(this) {
            showToast(it)
        }
    }

    override fun initClick() {
        binding.swipeRefresh.setOnRefreshListener {
            model.listEmployee()
//            model.listEmployee = null
//            model.showLoader = null
        }
    }

    private fun createAdapter() {
        val layoutManager = LinearLayoutManager(activity(), RecyclerView.VERTICAL, false)
        binding.rvPosts.layoutManager = layoutManager
    }
}
