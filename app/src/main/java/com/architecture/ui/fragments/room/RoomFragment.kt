package com.architecture.ui.fragments.room

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.architecture.R
import com.architecture.data.wrapper.User
import com.architecture.databinding.DialogAddUserBinding
import com.architecture.databinding.DialogShowDetailsBinding
import com.architecture.databinding.FragmentRoomBinding
import com.architecture.ui.fragments.base.BaseFragment
import com.architecture.utils.Log

class RoomFragment : BaseFragment() {

    private val model by lazy { ViewModelProvider(this).get(RoomViewModel::class.java) }
    private lateinit var binding : FragmentRoomBinding
    private lateinit var adapter: RoomAdapter
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = setFragmentLayout(R.layout.fragment_room, container)

//        return inflater.inflate(R.layout.fragment_room, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
    }

    override fun initView() {

        createAdapter()

        model.usersList?.observe(viewLifecycleOwner) {

                adapter = RoomAdapter(activity(), object : AdapterClickListener{

                    override fun onViewClick(position: Int) {
                        val user = it[position]
                        val showInfoBinding = DataBindingUtil.inflate<DialogShowDetailsBinding>(
                            LayoutInflater.from(activity()),
                            R.layout.dialog_show_details, null, false
                        )
                        val showInfo = Dialog(activity()).apply {
                            setContentView(showInfoBinding.root)
                            window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                            setCanceledOnTouchOutside(false)
                        }
                        showInfoBinding.model = user

                        showInfoBinding.btnOk.setOnClickListener {
                            showInfo.dismiss()
                        }
                        showInfo.show()
                    }

                    override fun onEditClick(position: Int) {
                        val user = it[position]
                        Log.i("TAG", user.toString())
                        openDialog(false, user)
                    }

                    override fun onDeleteClick(position: Int) {
                        val user = it[position]
                        model.delete(user)
                    }
                }, it)

                binding.adapter = adapter
//                binding.recyclerList.adapter = adapter
            }

        model.toastMsg.observe(viewLifecycleOwner) { showToast(it) }

        model.flagDialog.observe(viewLifecycleOwner) {
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
            model.deleteAll()
        }
    }

    private fun createAdapter() {
        val layoutManager = LinearLayoutManager(activity(), RecyclerView.VERTICAL, false)
        binding.recyclerList.layoutManager = layoutManager
    }

    private fun openDialog(flag: Boolean, userUpdate: User?) {

        val dialogBinding = DataBindingUtil.inflate<DialogAddUserBinding>(LayoutInflater.from(activity()),
            R.layout.dialog_add_user,null,false)

        dialog = Dialog(activity()).apply {
            setContentView(dialogBinding.root)
            window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
            setCanceledOnTouchOutside(false)
            setCancelable(true)
        }

        dialogBinding.model = model

        if (flag) {
            dialogBinding.headerText.text = getString(R.string.add_user)
            dialogBinding.btnAddUpdate.text = getString(R.string.add)
            model.run {
                name.value = ""
                age.value = ""
                salary.value = ""
            }
        } else {
            dialogBinding.headerText.text = getString(R.string.update_user)
            dialogBinding.btnAddUpdate.text = getString(R.string.update)
            dialogBinding.edtName.isEnabled = false

            userUpdate!!.let {
                model.run {
                    name.value = userUpdate.name
                    age.value = userUpdate.age.toString()
                    salary.value = userUpdate.salary.toString()
                }
            }
        }

        dialogBinding.btnAddUpdate.setOnClickListener {
            model.addUpdateUser(flag)
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }

}
