package com.architecture.ui.activity.base

import android.app.ProgressDialog
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract fun initView()
    abstract fun initClick()

    fun <T : ViewDataBinding> setLayout(layout : Int) : T {
        return DataBindingUtil.setContentView(this, layout)
    }

    private lateinit var dialog: ProgressDialog
    fun showLoading() {

        dialog = ProgressDialog(this).apply {
            setCanceledOnTouchOutside(false)
            setMessage("Please wait...")
        }
        if(!dialog.isShowing){
            dialog.show()
        }
    }

    fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun View.visible(){
        this.visibility = View.VISIBLE
    }
}