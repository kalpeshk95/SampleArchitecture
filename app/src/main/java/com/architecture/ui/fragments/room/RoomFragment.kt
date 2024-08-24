package com.architecture.ui.fragments.room

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.architecture.R
import com.architecture.databinding.DialogAddUserBinding
import com.architecture.databinding.DialogShowDetailsBinding
import com.architecture.databinding.FragmentRoomBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.wrapper.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomFragment : BaseFragment(R.layout.fragment_room), AdapterClickListener {

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RoomViewModel>()
    private val roomAdapter by lazy { RoomAdapter(this) }
    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        observer()
    }

    override fun initView() {
        with(binding.recyclerList) {
            layoutManager = LinearLayoutManager(context)
            adapter = roomAdapter
        }
    }

    override fun initClick() {
        binding.add.setOnClickListener { openDialog(true, null) }
        binding.deleteAll.setOnClickListener { viewModel.deleteAll() }
    }

    private fun observer() {
        with(viewModel) {
            showLoader.observe(viewLifecycleOwner) {
                progressBarVisibility?.setVisibility(if (it) View.VISIBLE else View.GONE)
            }
            usersList.observe(viewLifecycleOwner, roomAdapter::setItems)
            toastMsg.observe(viewLifecycleOwner, ::showToast)
            flagDialog.observe(viewLifecycleOwner) { if (it) dialog?.cancel() }
        }
    }

    private fun openDialog(isAdding: Boolean, userToUpdate: User?) {
        val dialogBinding = DialogAddUserBinding.inflate(layoutInflater)
        dialog = Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)
        }

        with(dialogBinding) {
            headerText.text = getString(if (isAdding) R.string.add_user else R.string.update_user)
            btnAddUpdate.text = getString(if (isAdding) R.string.add else R.string.update)
            if (!isAdding) {
                edtName.isEnabled = false
                userToUpdate?.let {
                    edtName.setText(it.name)
                    edtAge.setText(it.age.toString())
                    edtSalary.setText(it.salary.toString())
                }
            }
            btnAddUpdate.setOnClickListener {
                with(viewModel) {
                    name.value = edtName.text.toString()
                    age.value = edtAge.text.toString()
                    salary.value = edtSalary.text.toString()
                    addUpdateUser(isAdding)
                }
            }
            btnCancel.setOnClickListener { dialog?.cancel() }
        }
        dialog?.show()
    }

    override fun onViewClick(user: User) {
        val showInfoBinding = DialogShowDetailsBinding.inflate(layoutInflater)
        Dialog(requireContext()).apply {
            setContentView(showInfoBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            show()
            with(showInfoBinding) {
                tvName.text = user.name
                tvAge.text = user.age.toString()
                tvSalary.text = user.salary.toString()
                btnOk.setOnClickListener { dismiss() }
            }
        }
    }

    override fun onEditClick(user: User) = openDialog(false, user)

    override fun onDeleteClick(user: User) {
        viewModel.delete(user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog = null // Clear dialog reference to avoid leaks
    }
}