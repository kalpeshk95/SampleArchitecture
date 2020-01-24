package com.architecture.ui.fragments.base

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.architecture.ui.activity.main.MainActivity

abstract class BaseFragment : Fragment(){

    abstract fun initView()
    abstract fun initClick()

    fun activity() = activity as MainActivity

    fun <T : ViewDataBinding> setFragmentLayout(layout: Int, container: ViewGroup?): T {

        return DataBindingUtil.inflate(layoutInflater, layout, container, false)
    }

//    val navController = activity().navController

    fun showToast(msg : String) = activity().showToast(msg)

    fun showLoading() = activity().showLoading()

    fun hideLoading() = activity().hideLoading()

//    fun goToSettings() = activity().goToSettings()
//
//    fun finish() = activity().finish()
//
//    fun hasPermissions(permissions: Array<String>): Boolean {
//        for (permission in permissions) {
//            if (ActivityCompat.checkSelfPermission(requireContext(), permission) !=
//                PackageManager.PERMISSION_GRANTED) {
//                return false
//            }
//        }
//        return true
//    }
//
//    fun backPress() = activity().onBackPressed()

}
