package com.architecture.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.R
import com.architecture.databinding.FragmentMenuBinding
import com.architecture.network.Resource
import com.architecture.ui.activity.login.LoginViewModel
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Log
import com.crazylegend.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

//    private val viewModel by lazy { ViewModelProvider(this).get(MenuViewModel::class.java) }

    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel by viewModel<MenuViewModel>()

    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {
        createAdapter()
        /*if (viewModel.listEmployee.value == null)*/ viewModel.listEmployee()

        viewModel.listEmployee.observe(viewLifecycleOwner, {

            when (it) {

                is Resource.Success -> {
                    it.data?.let { employeeList ->
                        menuAdapter.setItems(employeeList)
                        viewModel.showLoader.value = false
                        if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing =
                            false
                    }
                }

                is Resource.Loading -> {
//                    viewModel.showLoader.value = false
                }

                is Resource.Error -> {
                    viewModel.showLoader.value = false
                    viewModel.toastMsg.value = "Something went wrong"
                    Log.e("Fetch employee ex : ${it.exception}")
                }

            }
        })

        viewModel.showLoader.observe(viewLifecycleOwner, {
            progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.GONE)
        })

        viewModel.toastMsg.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    override fun initClick() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.listEmployee()
        }
    }

    private fun createAdapter() {
        menuAdapter = MenuAdapter()
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter = menuAdapter
        }
    }
}
