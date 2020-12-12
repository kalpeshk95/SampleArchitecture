package com.architecture.ui.fragments.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.architecture.R
import com.architecture.databinding.FragmentWorkBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.ui.workers.ThirdWorker

class WorkFragment : BaseFragment(R.layout.fragment_work) {

    private val viewModel by lazy { ViewModelProvider(this).get(WorkViewModel::class.java) }
//    private lateinit var binding: FragmentWorkBinding

    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentWorkBinding.inflate(inflater, container, false)
        _binding = binding

//        return inflater.inflate(R.layout.fragment_work, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {

        viewModel.result?.observe(viewLifecycleOwner, {
            binding.resultText.text = "${it.outputData.getInt(ThirdWorker.KEY, 0)}"
        })
    }

    override fun initClick() {

        binding.btnAdd.setOnClickListener {
            viewModel.run {
                firstNumber.value = binding.edtFirstNo.text.toString()
                secondNumber.value = binding.edtSecNo.text.toString()
            }
            viewModel.addRequest()
        }

        binding.btnPeriodic.setOnClickListener {
            viewModel.addPeriodicRequest()
        }
    }
}
