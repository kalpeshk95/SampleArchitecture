package com.architecture.ui.fragments.room

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.architecture.R
import com.architecture.databinding.DialogAddUserBinding
import com.architecture.databinding.DialogShowDetailsBinding
import com.architecture.databinding.FragmentRoomBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Constant
import com.architecture.wrapper.User
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RoomFragment : BaseFragment(R.layout.fragment_room), AdapterClickListener {

    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RoomViewModel>()

    private lateinit var roomAdapter: RoomAdapter
    private lateinit var dialog: Dialog

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
    }

    override fun initView() {

        createAdapter()

        viewModel.usersList?.observe(viewLifecycleOwner) {
            roomAdapter.setItems(it)
        }

        viewModel.toastMsg.observe(viewLifecycleOwner) { showToast(it) }

        viewModel.flagDialog.observe(viewLifecycleOwner) {
            if (it) {
                dialog.let { dialog ->
                    if (dialog.isShowing)
                        dialog.cancel()
                }
            }
        }
    }

    override fun initClick() {

        binding.add.setOnClickListener {
            openDialog(true, null)
        }

        binding.deleteAll.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    private fun createAdapter() {
        roomAdapter = RoomAdapter(this)
        binding.recyclerList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter = roomAdapter
        }
    }

    private fun openDialog(flag: Boolean, userUpdate: User?) {

        val dialogBinding = DialogAddUserBinding.inflate(LayoutInflater.from(requireContext()))

        dialog = Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
            setCancelable(true)
        }

        if (flag) {
            dialogBinding.run {
                headerText.text = getString(R.string.add_user)
                btnAddUpdate.text = getString(R.string.add)
                edtName.setText(getString(R.string.empty_string))
                edtAge.setText(getString(R.string.empty_string))
                edtSalary.setText(getString(R.string.empty_string))
            }
        } else {
            dialogBinding.run {
                headerText.text = getString(R.string.update_user)
                btnAddUpdate.text = getString(R.string.update)
                edtName.isEnabled = false
                edtName.setText(userUpdate!!.name)
                edtAge.setText(userUpdate.age.toString())
                edtSalary.setText(userUpdate.salary.toString())
            }
        }

        dialogBinding.btnAddUpdate.setOnClickListener {
            viewModel.run {
                name.value = dialogBinding.edtName.text.toString()
                age.value = dialogBinding.edtAge.text.toString()
                salary.value = dialogBinding.edtSalary.text.toString()
            }
            viewModel.addUpdateUser(flag)
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

    override fun onViewClick(user: User) {
        val showInfoBinding =
            DialogShowDetailsBinding.inflate(LayoutInflater.from(requireContext()))
        val showInfo = Dialog(requireContext()).apply {
            setContentView(showInfoBinding.root)
            window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCanceledOnTouchOutside(false)
        }
        showInfoBinding.tvName.text = user.name
        showInfoBinding.tvAge.text = user.age.toString()
        showInfoBinding.tvSalary.text = user.salary.toString()

        showInfoBinding.btnOk.setOnClickListener {
            showInfo.dismiss()
        }
        showInfo.show()
    }

    override fun onEditClick(user: User) {
        Timber.i(Constant.TAG, user.toString())
        openDialog(false, user)
    }

    override fun onDeleteClick(user: User) {
        viewModel.delete(user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
