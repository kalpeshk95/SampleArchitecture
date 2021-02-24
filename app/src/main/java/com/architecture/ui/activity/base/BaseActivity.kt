package com.architecture.ui.activity.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract fun initView()
    abstract fun initClick()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

//    fun <T : ViewDataBinding> setLayout(layout : Int) : T {
//        return DataBindingUtil.setContentView(this, layout)
//    }

}