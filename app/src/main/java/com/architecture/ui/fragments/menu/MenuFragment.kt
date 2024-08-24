package com.architecture.ui.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.architecture.R
import com.architecture.databinding.FragmentMenuBinding
import com.architecture.network.Resource
import com.architecture.ui.fragments.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MenuFragment : BaseFragment(R.layout.fragment_menu) {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MenuViewModel>()
    private val menuAdapter by lazy { MenuAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()
    }

    override fun initView() {
        with(binding.rvPosts) {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }
    }

    override fun initClick() {
        binding.swipeRefresh.setOnRefreshListener(viewModel::fetchEmployees)
    }

    private fun observer() {
        with(viewModel) {
            listEmployee.observe(viewLifecycleOwner) { it ->
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { employeeList ->
                            employeeList.let(menuAdapter::setItems)
                            hideLoading()
                        }
                    }

                    is Resource.Loading -> showLoading()

                    is Resource.Error -> {
                        hideLoading()
                        toastMsg.value = "Something went wrong"
                        Timber.e("Fetch employee ex : ${it.exception}")
                    }

                }
            }
            toastMsg.observe(viewLifecycleOwner, ::showToast)
        }
    }

    private fun showLoading() {
        progressBarVisibility?.setVisibility(View.VISIBLE)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        progressBarVisibility?.setVisibility(View.GONE)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}