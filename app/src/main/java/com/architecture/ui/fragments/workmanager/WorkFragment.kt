package com.architecture.ui.fragments.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

import com.architecture.R
import com.architecture.databinding.FragmentWorkBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.ui.workers.ThirdWorker

class WorkFragment : BaseFragment() {

    private val model by lazy { ViewModelProvider(this).get(WorkViewModel::class.java) }
    private lateinit var binding: FragmentWorkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = setFragmentLayout(R.layout.fragment_work, container)
        binding.model = model

//        return inflater.inflate(R.layout.fragment_work, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {

        model.result?.observe(viewLifecycleOwner) {
            binding.resultText.text = "${it.outputData.getInt(ThirdWorker.KEY,0)}"
        }
    }

    override fun initClick() {

    }
}
