package com.architecture.ui.fragments.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.architecture.R
import com.architecture.databinding.FragmentWorkBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.ui.fragments.room.RoomViewModel
import com.architecture.ui.workers.ThirdWorker
import com.crazylegend.viewbinding.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkFragment : BaseFragment(R.layout.fragment_work) {

    private val binding by viewBinding(FragmentWorkBinding::bind)
    private val viewModel by viewModel<WorkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_work, container, false)
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
