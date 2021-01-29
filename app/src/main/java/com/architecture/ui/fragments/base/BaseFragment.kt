package com.architecture.ui.fragments.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.architecture.ui.activity.main.MainActivity

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract fun initView()
    abstract fun initClick()

    private fun activity() = activity as MainActivity

//    fun <T : ViewDataBinding> setFragmentLayout(layout: Int, container: ViewGroup?): T {
//
//        return DataBindingUtil.inflate(layoutInflater, layout, container, false)
//    }

    fun showToast(msg: String) = activity().showToast(msg)

    var progressBarVisibility: ShowProgressBar? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShowProgressBar) {
            progressBarVisibility = context
        } else {
            throw RuntimeException("$context must implement ShowProgressBar")
        }
    }

    interface ShowProgressBar {
        fun setVisibility(visibility: Int)
    }

}
