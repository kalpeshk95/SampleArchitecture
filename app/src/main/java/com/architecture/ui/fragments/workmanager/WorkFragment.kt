package com.architecture.ui.fragments.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.architecture.R
import com.architecture.databinding.FragmentWorkBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.ui.workers.ThirdWorker
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkFragment : BaseFragment(R.layout.fragment_work) {

    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {

        viewModel.result?.observe(viewLifecycleOwner) {
            binding.resultText.text = it?.outputData?.getInt(ThirdWorker.KEY, 0).toString()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
