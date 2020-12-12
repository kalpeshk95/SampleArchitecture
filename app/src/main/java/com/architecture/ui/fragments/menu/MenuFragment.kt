package com.architecture.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.databinding.FragmentMenuBinding
import com.architecture.network.Resource
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Log

class MenuFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(MenuViewModel::class.java) }

    //    private lateinit var binding: FragmentMenuBinding
    private lateinit var menuAdapter: MenuAdapter

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMenuBinding.inflate(inflater, container, false)
        _binding = binding

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
        /*if (viewModel.listEmployee.value == null)*/ viewModel.listEmployee()

        viewModel.listEmployee.observe(viewLifecycleOwner) {

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
//                    viewModel.showLoader.value = false
                    viewModel.toastMsg.value = "Something went wrong"
                    Log.e("Fetch employee ex : ${it.exception}")
                }

            }

//            it?.let { employeeList ->
//                menuAdapter.setItems(employeeList)
//                if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
//            }

        }

        viewModel.showLoader.observe(viewLifecycleOwner) {
            if (it) showLoading() else hideLoading()
        }

        viewModel.toastMsg.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    override fun initClick() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.listEmployee()
//            model.listEmployee = null
//            model.showLoader = null
        }
    }

    private fun createAdapter() {
        menuAdapter = MenuAdapter()
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter = menuAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
